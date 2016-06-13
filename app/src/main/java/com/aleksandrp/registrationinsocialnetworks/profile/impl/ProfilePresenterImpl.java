package com.aleksandrp.registrationinsocialnetworks.profile.impl;

import android.content.Context;

import com.aleksandrp.registrationinsocialnetworks.entity.User;
import com.aleksandrp.registrationinsocialnetworks.profile.ProfilePresenter;
import com.aleksandrp.registrationinsocialnetworks.profile.ProfileView;
import com.aleksandrp.registrationinsocialnetworks.realm.ServiceRealm;
import com.aleksandrp.registrationinsocialnetworks.realm.inpl.RealmImpl;

/**
 * Created by AleksandrP on 13.06.2016.
 */
public class ProfilePresenterImpl implements ProfilePresenter {

    private ServiceRealm mRealm;

    @Override
    public void loadDataFromDb(Context mContext, ProfileView mView, String mId) {

        mRealm = RealmImpl.getInstance(mContext);
        User mUser = mRealm.getUserFromDb(mId);
        mView.showAllParams(mUser);

    }

    @Override
    public void deleteUser(Context mContext,String mS) {
        mRealm = RealmImpl.getInstance(mContext);
        mRealm.removeUser(mS);
    }
}