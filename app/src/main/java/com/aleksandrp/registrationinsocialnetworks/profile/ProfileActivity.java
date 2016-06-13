package com.aleksandrp.registrationinsocialnetworks.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleksandrp.registrationinsocialnetworks.R;
import com.aleksandrp.registrationinsocialnetworks.entity.User;
import com.aleksandrp.registrationinsocialnetworks.profile.impl.ProfilePresenterImpl;
import com.aleksandrp.registrationinsocialnetworks.utils.StaticParams;
import com.aleksandrp.registrationinsocialnetworks.utils.UtilsApp;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements
        View.OnClickListener, ProfileView {

    private TextView tvName, tvId, tvBirth, tvEmail;
    private ImageView ivIcon;

    private ProfilePresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUi();

        String id = getIntent().getStringExtra(StaticParams.ID_USER);

        mPresenter = new ProfilePresenterImpl();
        mPresenter.loadDataFromDb(ProfileActivity.this, this, id);
    }

    private void initUi() {
        findViewById(R.id.bt_delete).setOnClickListener(this);
        findViewById(R.id.bt_edit).setOnClickListener(this);

        ivIcon = (ImageView) findViewById(R.id.iv_icon);

        tvId = (TextView) findViewById(R.id.tv_id_user);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvEmail = (TextView) findViewById(R.id.tv_e_mail_user);
        tvBirth = (TextView) findViewById(R.id.tv_birth_user);
    }

    @Override
    public void onClick(View v) {
        UtilsApp.disableDoubleClick(v);
        switch (v.getId()) {
            case R.id.bt_delete:
                mPresenter.deleteUser(ProfileActivity.this, tvId.getText().toString());
                finish();
                break;
            case R.id.bt_edit:

                break;
        }
    }

    @Override
    public void showAllParams(User mUser) {
        Picasso.with(ProfileActivity.this)
                .load(mUser.getIcon())
                .error(R.mipmap.ic_launcher)
                .into(ivIcon);

        tvId.setText(mUser.getId());
        tvName.setText(mUser.getName());
        tvEmail.setText(mUser.getE_mail());
        tvBirth.setText(mUser.getBirth());
    }
}
