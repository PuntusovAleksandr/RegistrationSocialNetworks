package com.aleksandrp.registrationinsocialnetworks.gallery;

/**
 * Created by AleksandrP on 14.06.2016.
 */
public interface GalleryActivityView {

    void showProgress();

    void hideProgress();

    void startShowImages(int mCountIcon, String[] mUrlPhotos);
}
