package com.aleksandrp.registrationinsocialnetworks.login.twittter;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import retrofit.http.Query;
import retrofit2.http.GET;

/**
 * Created by AleksandrP on 13.06.2016.
 */
public class MyTwitterApiClient extends TwitterApiClient {
    public MyTwitterApiClient(TwitterSession session) {
        super(session);
    }

    /**
     * Provide CustomService with defined endpoints
     */
    public CustomService getCustomService() {
        return getService(CustomService.class);
    }
}

// example users/show service endpoint
interface CustomService {
    @GET("/1.1/users/show.json")
    void show(@Query("user_id") long id, Callback<User> cb);
}