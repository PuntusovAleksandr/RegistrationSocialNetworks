package com.aleksandrp.registrationinsocialnetworks.realm.inpl;

import android.content.Context;
import android.content.SharedPreferences;

import com.aleksandrp.registrationinsocialnetworks.entity.User;
import com.aleksandrp.registrationinsocialnetworks.realm.ServiceRealm;
import com.aleksandrp.registrationinsocialnetworks.realm.entity.UserRealm;
import com.aleksandrp.registrationinsocialnetworks.utils.StaticParams;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by AleksandrP on 11.06.2016.
 */
public class RealmImpl implements ServiceRealm {

    private static RealmImpl sRealm;
    private Context mContext;
    private Realm realm;

    public static RealmImpl getInstance(Context mContext) {
        if (sRealm == null) {
            sRealm = new RealmImpl(mContext);
        }
        return sRealm;
    }

    private RealmImpl(Context mContext) {
        this.mContext = mContext;
        if (realm == null) {
            setRealmData(mContext);
        }
    }

    private void setRealmData(Context context) {
        String nameDB = mContext.getApplicationInfo().getClass().getName();
        realm = Realm.getInstance(
                new RealmConfiguration.Builder(context)
                        .name(nameDB)
                        .schemaVersion(StaticParams.VERSION_DB)
                        .build()
        );
    }


    @Override
    public void putUserInDb(User mUser, String keySocialNetwork) {
        UserRealm mUserRealm = new UserRealm();
        mUserRealm.setId(mUser.getId());
        mUserRealm.setName(mUser.getName());
        mUserRealm.setE_mail(mUser.getE_mail());
        mUserRealm.setBirth(mUser.getBirth());
        mUserRealm.setIcon(mUser.getIcon());
        mUserRealm.setKey_network(keySocialNetwork);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(mUserRealm);
        realm.commitTransaction();

    }

    @Override
    public User getUserFromDb(String userId) {
        UserRealm userRrealm = realm.where(UserRealm.class)
                .equalTo("id", userId)
                .findFirst();
        return new User(
                String.valueOf(userRrealm.getId()),
                userRrealm.getName(),
                userRrealm.getE_mail(),
                userRrealm.getBirth(),
                userRrealm.getIcon()
        );
    }

    @Override
    public void removeUser(String mI) {
        // TODO: 13.06.2016 remove user
        final UserRealm mUserRealm = realm.where(UserRealm.class)
                .equalTo("id", mI)
                .findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mUserRealm.removeFromRealm();
            }
        });
    }

    @Override
    public void editUser(User mUser) {
        realm.beginTransaction();
        UserRealm mUserRealm = realm.where(UserRealm.class)
                .equalTo("id", mUser.getId())
                .findFirst();

        mUserRealm.setName(mUser.getName());
        mUserRealm.setBirth(mUser.getBirth());
        mUserRealm.setE_mail(mUser.getE_mail());
        realm.commitTransaction();
    }

    @Override
    public boolean isUserEmpry(String mId) {
        return realm.where(UserRealm.class)
                .equalTo("id", mId)
                .findFirst() == null;
    }

}
