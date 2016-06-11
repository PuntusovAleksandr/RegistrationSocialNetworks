package com.aleksandrp.registrationinsocialnetworks.login;

/**
 * Created by AleksandrP on 11.06.2016.
 */
public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUserError();

    void goToProfile(String mId);

}
