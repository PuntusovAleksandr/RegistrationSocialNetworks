package com.aleksandrp.registrationinsocialnetworks.realm.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AleksandrP on 11.06.2016.
 */
public class UserRealm extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String e_mail;
    private String birth;
    private String icon;
    private String key_network;

    public UserRealm() {
    }

    public String getKey_network() {
        return key_network;
    }

    public void setKey_network(String mKey_network) {
        key_network = mKey_network;
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
