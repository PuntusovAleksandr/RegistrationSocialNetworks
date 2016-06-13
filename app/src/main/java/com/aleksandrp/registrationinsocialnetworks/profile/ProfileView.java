package com.aleksandrp.registrationinsocialnetworks.profile;

import com.aleksandrp.registrationinsocialnetworks.entity.User;

/**
 * Created by AleksandrP on 13.06.2016.
 */
public interface ProfileView {

    void showAllParams(User mUser);

    void showDialog(User mUser);

    void updateUi();
}
