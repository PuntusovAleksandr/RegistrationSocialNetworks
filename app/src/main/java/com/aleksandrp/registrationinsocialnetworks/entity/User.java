package com.aleksandrp.registrationinsocialnetworks.entity;

/**
 * Created by AleksandrP on 11.06.2016.
 */
public class User {

    private String id;
    private String name;
    private String e_mail;
    private String birth;
    private String icon;

    public User() {
    }

    public User(String mId, String mName, String mE_mail, String mBirth, String mIcon) {
        id = mId;
        name = mName;
        e_mail = mE_mail;
        birth = mBirth;
        icon = mIcon;
    }

    public String getId() {
        return id;
    }

    public void setId(String mId) {
        id = mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        name = mName;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String mE_mail) {
        e_mail = mE_mail;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String mBirth) {
        birth = mBirth;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String mIcon) {
        icon = mIcon;
    }
}
