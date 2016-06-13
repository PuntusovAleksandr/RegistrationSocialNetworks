package com.aleksandrp.registrationinsocialnetworks.realm;

import com.aleksandrp.registrationinsocialnetworks.entity.User;

/**
 * Created by AleksandrP on 11.06.2016.
 */
public interface ServiceRealm {

    void putUserInDb(User mUser, String keySocialNetwork);

    User getUserFromDb(String userId);

    void removeUser(String mI);

    void editUser(User mUser);
}
