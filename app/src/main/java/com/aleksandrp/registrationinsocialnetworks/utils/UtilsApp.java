package com.aleksandrp.registrationinsocialnetworks.utils;

import android.os.Handler;
import android.view.View;

/**
 * Created by AleksandrP on 11.06.2016.
 */
public class UtilsApp {

    /**
     * disable double click
     *
     * @param mView
     */
    public static void disableDoubleClick(final View mView) {
        mView.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.setEnabled(true);
            }
        }, 250);
    }
}
