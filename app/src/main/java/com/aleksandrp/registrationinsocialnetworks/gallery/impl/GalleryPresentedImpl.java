package com.aleksandrp.registrationinsocialnetworks.gallery.impl;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.aleksandrp.registrationinsocialnetworks.entity.User;
import com.aleksandrp.registrationinsocialnetworks.gallery.GalleryActivityView;
import com.aleksandrp.registrationinsocialnetworks.gallery.GalleryPresented;
import com.aleksandrp.registrationinsocialnetworks.login.MainActivity;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPhotoArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AleksandrP on 14.06.2016.
 */
public class GalleryPresentedImpl implements GalleryPresented {

    private int countIcon;
    private String[] urlPhotos;


    @Override
    public void loadGallery(String mIdUser, Context mContext, final GalleryActivityView mActivityView) {
        mActivityView.showProgress();


        VKRequest request = new VKRequest("photos.getAll",
                VKParameters.from(VKApiConst.OWNER_ID, 1),
                VKPhotoArray.class);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                JSONObject mObject = response.json;
                try {
//                        mObject.getString("count");
                    JSONObject mResponse = mObject.getJSONObject("response");
//                        countIcon = mResponse.getJSONObject(0).getInt("count");
                    JSONArray mJSONArray = mResponse.getJSONArray("items");
                    countIcon = mJSONArray.length();
                    urlPhotos = new String[countIcon];
                    for (int i = 0; i < countIcon; i++) {
                        urlPhotos[i] = mJSONArray.getJSONObject(i).getString("photo_604");
                    }

                    mActivityView.hideProgress();
                    mActivityView.startShowImages(countIcon, urlPhotos);

                } catch (JSONException mE) {
                    mE.printStackTrace();
                }

            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                mActivityView.hideProgress();
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                mActivityView.hideProgress();
                super.onError(error);
            }
        });
    }
}
