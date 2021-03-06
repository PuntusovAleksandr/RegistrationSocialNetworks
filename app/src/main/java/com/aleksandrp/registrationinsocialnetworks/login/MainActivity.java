package com.aleksandrp.registrationinsocialnetworks.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aleksandrp.registrationinsocialnetworks.R;
import com.aleksandrp.registrationinsocialnetworks.login.impl.LoginPresenterImpl;
import com.aleksandrp.registrationinsocialnetworks.profile.ProfileActivity;
import com.aleksandrp.registrationinsocialnetworks.utils.StaticParams;
import com.aleksandrp.registrationinsocialnetworks.utils.UtilsApp;

public class MainActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private ProgressBar mProgressBar;
    private TextView validation;

    private LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        presenter = new LoginPresenterImpl(MainActivity.this, this);
    }

    private void initUi() {
        validation = (TextView) findViewById(R.id.validation);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_main);

        findViewById(R.id.ib_fb).setOnClickListener(this);
        findViewById(R.id.ib_twitter).setOnClickListener(this);
        findViewById(R.id.ib_vk).setOnClickListener(this);
        findViewById(R.id.ib_google).setOnClickListener(this);
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
    public void goToProfile(String mId) {
        Intent mIntent = new Intent(MainActivity.this, ProfileActivity.class);
        mIntent.putExtra(StaticParams.ID_USER, mId);
        startActivity(mIntent);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == StaticParams.FB_CODE) {
                presenter.onActivityResultFB(requestCode, resultCode, data);
            }
            if (requestCode == StaticParams.VK_CODE) {
                presenter.onActivityResultVK(requestCode, resultCode, data);
            }
            if (requestCode == StaticParams.GOOGLE_CODE) {
                presenter.onActivityResultGoogle(requestCode, resultCode, data);
            }
            if (requestCode == StaticParams.TWITTER_CODE) {
                presenter.onActivityResultTwitter(requestCode, resultCode, data);
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

}
