package com.aleksandrp.registrationinsocialnetworks.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.aleksandrp.registrationinsocialnetworks.entity.User;
import com.aleksandrp.registrationinsocialnetworks.realm.ServiceRealm;
import com.aleksandrp.registrationinsocialnetworks.realm.inpl.RealmImpl;
import com.aleksandrp.registrationinsocialnetworks.utils.StaticParams;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by AleksandrP on 11.06.2016.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mLoginView;
    private CallbackManager mCallbackManager;
    private Context mContext;
    private User mUser = null;
    private ServiceRealm mRealm;


    public LoginPresenterImpl(Context mContext, LoginView mLoginView) {
        this.mContext = mContext;
        this.mLoginView = mLoginView;
    }

    @Override
    public void connectToFB(final Context mContext, String socNetwork) {
        showProgress();
        registeredFaceBook(mContext);
    }


    @Override
    public void connectToTwitter(String socNetwork) {
        showProgress();
    }

    @Override
    public void connectToVK(String socNetwork) {
        showProgress();
        // add permissions
        String[] scope = new String[]{
                VKScope.EMAIL, VKScope.DIRECT, VKScope.NOHTTPS, VKScope.NOTIFY, VKScope.STATS};
        VKSdk.login((MainActivity) mContext, scope);
    }

    @Override
    public void connectToGOOGLE(String socNetwork) {
        showProgress();
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
    }

    @Override
    public void onActivityResultFB(int mRequestCode, int mResultCode, Intent mData) {
        mCallbackManager.onActivityResult(mRequestCode, mResultCode, mData);
    }

    @Override
    public void onActivityResultVK(int mRequestCode, int mResultCode, Intent mData) {
        if (!VKSdk.onActivityResult(mRequestCode, mResultCode, mData,
                new VKCallback<VKAccessToken>() {
                    @Override
                    public void onResult(VKAccessToken res) {
                        // register successful
                        registerSuccessfulVK(res);
                    }

                    @Override
                    public void onError(VKError error) {
                        // error authorisation
                        hideProgress();
                        errorUser();
                    }
                })) {
        }
    }

    private void registerSuccessfulVK(final VKAccessToken res) {
        VKRequest request =
                VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "bdate, photo_max_orig"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                JSONObject mJsonAll = response.json;
                try {
                    JSONArray mResponse = mJsonAll.getJSONArray("response");
                    JSONObject mJson = mResponse.getJSONObject(0);
                    mUser = new User(
                            res.userId,
                            mJson.getString("first_name") + " " + mJson.getString("last_name"),
                            res.email,
                            mJson.getString("bdate"),
                            mJson.getString("photo_max_orig")
                    );
                } catch (JSONException mE) {
                    mE.printStackTrace();
                }

                hideProgress();
                if (mUser != null) {
                    mRealm = RealmImpl.getInstance(mContext);
                    mRealm.putUserInDb(mUser, StaticParams.VK);
                    goToProfile(mUser.getId());
                } else errorUser();
            }

            @Override
            public void onError(VKError error) {
                hideProgress();
                errorUser();
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                hideProgress();
                errorUser();
            }
        });
    }

    private void showProgress() {
        if (mLoginView != null) {
            mLoginView.showProgress();
        }
    }

    private void hideProgress() {
        if (mLoginView != null) {
            mLoginView.hideProgress();
        }
    }

    private void errorUser() {
        if (mLoginView != null) {
            mLoginView.setUserError();
        }
    }

    private void goToProfile(String mId) {
        if (mLoginView != null) {
            mLoginView.goToProfile(mId);
        }
    }


    private void registeredFaceBook(final Context mContext) {
        // init FB
        FacebookSdk.sdkInitialize(mContext.getApplicationContext());
        AppEventsLogger.activateApp(mContext);
        mCallbackManager = CallbackManager.Factory.create();

//        String pic_link="https://graph.facebook.com/" + id + "/picture?type=large";

        LoginManager mInstance = LoginManager.getInstance();
        mInstance
                .logInWithReadPermissions((MainActivity) mContext,
                        Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
        mInstance
                .registerCallback(mCallbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                                hideProgress();
                                AccessToken mAccessToken = loginResult.getAccessToken();
                                GraphRequest request = GraphRequest.newMeRequest(
                                        mAccessToken,
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(
                                                    JSONObject object,
                                                    GraphResponse response) {
                                                // Application code
                                                try {
                                                    String mId = object.getString("id");
                                                    mUser = new User(
                                                            mId,
                                                            object.getString("name"),
                                                            object.getString("email"),
                                                            object.getString("birthday"),
                                                            "https://graph.facebook.com/" +
                                                                    mId + "/picture?type=large"
                                                    );
                                                } catch (JSONException mE) {
                                                    mE.printStackTrace();
                                                }
                                                if (mUser != null) {
                                                    mRealm = RealmImpl.getInstance(mContext);
                                                    mRealm.putUserInDb(mUser, StaticParams.FB);
                                                    goToProfile(mUser.getId());
                                                } else errorUser();
                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "birthday,email,id,name");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onCancel() {
                                // App code
                                hideProgress();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                                hideProgress();
                                errorUser();
                            }
                        });
    }
}
