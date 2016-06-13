package com.aleksandrp.registrationinsocialnetworks.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aleksandrp.registrationinsocialnetworks.R;
import com.aleksandrp.registrationinsocialnetworks.entity.User;
import com.aleksandrp.registrationinsocialnetworks.profile.ProfilePresenter;
import com.aleksandrp.registrationinsocialnetworks.profile.ProfileView;
import com.aleksandrp.registrationinsocialnetworks.utils.UtilsApp;


/**
 * Created by zloj on 29.03.16.
 */
public class DialogEdit extends AlertDialog implements
        View.OnClickListener, DialogEditPresenter {

    private User mUser;
    private ProfileView mProfileView;
    private Context mContext;
    private EditText etName;
    private EditText etMail;
    private EditText etBirth;

    private ProfilePresenter mPresenter;

    public DialogEdit(Context mContext, User mUser, ProfileView mProfileView, ProfilePresenter mPresenter) {
        super(mContext);
        // Init dialog view
        LayoutInflater inflater = getLayoutInflater();
        this.mUser = mUser;
        this.mProfileView = mProfileView;
        this.mContext = mContext;
        this.mPresenter = mPresenter;

        View view = inflater.inflate(R.layout.context_dialog, null);

        initUi(view);
    }

    private void initUi(View view) {
        view.findViewById(R.id.bt_dialog_ok).setOnClickListener(this);
        view.findViewById(R.id.bt_dialog_cancel).setOnClickListener(this);

         etName = (EditText) view.findViewById(R.id.tv_dialog_name);
         etMail = (EditText) view.findViewById(R.id.tv_dialog_mail);
         etBirth = (EditText) view.findViewById(R.id.tv_dialog_birth);

        etName.setText(mUser.getName());
        etBirth.setText(mUser.getBirth());
        etMail.setText(mUser.getE_mail());

        this.setView(view);
    }

    @Override
    public void onClick(View v) {
        UtilsApp.disableDoubleClick(v);
        switch (v.getId()) {
            case R.id.bt_dialog_ok:
                mUser.setName(etName.getText().toString());
                mUser.setBirth(etBirth.getText().toString());
                mUser.setE_mail(etMail.getText().toString());
                mPresenter.editUser(mUser, mContext, this);
                break;

            case R.id.bt_dialog_cancel:
                this.dismiss();
                break;
        }
    }

    @Override
    public void closeDialog() {
        mProfileView.updateUi();
        this.dismiss();
    }
}
