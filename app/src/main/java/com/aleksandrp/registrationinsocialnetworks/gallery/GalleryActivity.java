package com.aleksandrp.registrationinsocialnetworks.gallery;

import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.aleksandrp.registrationinsocialnetworks.R;
import com.aleksandrp.registrationinsocialnetworks.gallery.adapter.TabAdapter;
import com.aleksandrp.registrationinsocialnetworks.gallery.fragment.ImageFragment;
import com.aleksandrp.registrationinsocialnetworks.gallery.impl.GalleryPresentedImpl;
import com.aleksandrp.registrationinsocialnetworks.utils.StaticParams;

public class GalleryActivity extends AppCompatActivity implements
        GalleryActivityView,
        ImageFragment.ListenerDisableActivity {

    private GalleryPresented mGalleryPresented;
    private ProgressBar mProgressBar;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mProgressBar = (ProgressBar) findViewById(R.id.id_progress_gallery);
        loadData();
    }


    private void loadData() {
        String idUser = getIntent().getStringExtra(StaticParams.EXTRA_ID_USER);

        mGalleryPresented = new GalleryPresentedImpl();
        mGalleryPresented.loadGallery(idUser, GalleryActivity.this, this);
    }

    @Override
    public void showProgress() {
        if (mProgressBar.getVisibility() == View.GONE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void startShowImages(int mCountIcon, String[] mUrlPhotos) {
        FragmentManager fragmentManager = getFragmentManager();
        mViewPager = (ViewPager) findViewById(R.id.pager);
        TabAdapter mTabAdapter = new TabAdapter(fragmentManager, mUrlPhotos.length, GalleryActivity.this, mUrlPhotos);
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableViewPager() {
        mViewPager.setEnabled(false);
    }

    @Override
    public void enableViewPager() {
        mViewPager.setEnabled(true);
    }
}
