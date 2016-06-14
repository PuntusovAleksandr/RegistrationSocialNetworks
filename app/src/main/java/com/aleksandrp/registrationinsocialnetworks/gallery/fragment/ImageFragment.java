package com.aleksandrp.registrationinsocialnetworks.gallery.fragment;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aleksandrp.registrationinsocialnetworks.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment implements View.OnTouchListener {

    private String url;
    private ImageView mImageView;
    private ListenerDisableActivity mDisableActivity;


//    +++++++++++++++++++++++++++++=
private static final String TAG = "Touch" ;
    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF start = new PointF();
    public static PointF mid = new PointF();

    // We can be in one of these 3 states
    public static final int NONE = 0;
    public static final int DRAG = 1;
    public static final int ZOOM = 2;
    public static int mode = NONE;

    float oldDist;

    private float[] matrixValues = new float[9];

//    =============================



    public ImageFragment(String mUpl, Context mContext) {
        this.url = mUpl;
        if (mContext instanceof ListenerDisableActivity) {
            mDisableActivity = (ListenerDisableActivity) mContext;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_image, container, false);

        mImageView = (ImageView) mView.findViewById(R.id.iv_fragment);

        mImageView.setOnTouchListener(this);

        setIcon();

        return mView;
    }

    private void setIcon() {
        Picasso.with(getActivity())
                .load(url)
                .into(mImageView);
    }




    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;


                mDisableActivity.disableViewPager();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                oldDist = spacing(event);
                if (oldDist > 10f) {

                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }

                mDisableActivity.disableViewPager();
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {

                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                }
                else if (mode == ZOOM) {

                    float newDist = spacing(event);
                    if (newDist > 10f) {

                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }

                mDisableActivity.disableViewPager();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:

                mode = NONE;

                mDisableActivity.enableViewPager();
                break;
        }

        // Perform the transformation
        view.setImageMatrix(matrix);

        return true; // indicate event was handled
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {

        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    public interface ListenerDisableActivity {
        void disableViewPager();
        void enableViewPager();
    }

}
