package com.aleksandrp.registrationinsocialnetworks.login;

import android.content.Context;
import android.content.Intent;

import com.facebook.CallbackManager;

/**
 * Created by AleksandrP on 11.06.2016.
 */
public interface LoginPresenter {

    void connectToFB(Context mContext, String socNetwork);
    void connectToTwitter(String socNetwork);
    void connectToVK(String socNetwork);
    void connectToGOOGLE(String socNetwork);

    void onDestroy();

    void onActivityResultFB(int mRequestCode, int mResultCode, Intent mData);

    void onActivityResultVK(int mRequestCode, int mResultCode, Intent mData);

    void onActivityResultGoogle(int mRequestCode, int mResultCode, Intent mData);

    void onActivityResultTwitter(int mRequestCode, int mResultCode, Intent mData);
}
