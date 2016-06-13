package com.aleksandrp.registrationinsocialnetworks.profile;

import android.content.Context;

/**
 * Created by AleksandrP on 13.06.2016.
 */
public interface ProfilePresenter {

    void loadDataFromDb(Context mContext, ProfileView mView, String mId);

    void deleteUser(Context mContext,String mS);
}
