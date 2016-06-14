package com.aleksandrp.registrationinsocialnetworks.profile;

import android.content.Context;

import com.aleksandrp.registrationinsocialnetworks.dialog.DialogEditPresenter;
import com.aleksandrp.registrationinsocialnetworks.entity.User;

/**
 * Created by AleksandrP on 13.06.2016.
 */
public interface ProfilePresenter {

    void loadDataFromDb(Context mContext, ProfileView mView, String mId);

    void deleteUser(Context mContext,String mS);

    void showDialogEdit(User mUser);

    void editUser(User mUser, Context mContext, DialogEditPresenter mPresenter);

    void goToGallery(ProfileView mProfileView);
}
