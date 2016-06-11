package com.aleksandrp.registrationinsocialnetworks.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aleksandrp.registrationinsocialnetworks.R;
import com.aleksandrp.registrationinsocialnetworks.profile.ProfileActivity;
import com.aleksandrp.registrationinsocialnetworks.utils.StaticParams;
import com.aleksandrp.registrationinsocialnetworks.utils.UtilsApp;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private ProgressBar mProgressBar;
    private TextView validation;

    private LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // init FB
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
//        callbackManager = CallbackManager.Factory.create();

        validation = (TextView) findViewById(R.id.validation);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_main);

        findViewById(R.id.ib_fb).setOnClickListener(this);
        findViewById(R.id.ib_twitter).setOnClickListener(this);
        findViewById(R.id.ib_vk).setOnClickListener(this);
        findViewById(R.id.ib_google).setOnClickListener(this);

        presenter = new LoginPresenterImpl(MainActivity.this, this);
    }

    @Override
    public void onClick(View v) {
        UtilsApp.disableDoubleClick(v);
        switch (v.getId()) {
            case R.id.ib_fb:
                presenter.connectToFB(MainActivity.this, StaticParams.FB);
                break;

            case R.id.ib_twitter:
                presenter.connectToTwitter(StaticParams.TWITTER);
                break;

            case R.id.ib_vk:
                presenter.connectToVK(StaticParams.VK);
                break;

            case R.id.ib_google:
                presenter.connectToGOOGLE(StaticParams.GOOGLE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUserError() {
        validation.setText(R.string.user_not_validation);
    }


    @Override
    public void goToProfile() {
        Intent mIntent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(mIntent);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == StaticParams.FB_CODE) {
                presenter.onActivityResultFB(requestCode, resultCode, data);
            }
        }
    }

}
