package com.viseator.unique;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by wudi.viseator on 2018/3/18.
 * Wu Di
 * wudi.viseator@bytedance.com
 */
public class UniqueView extends RelativeLayout{
    private static final String TAG = "@vir UniqueView";
    public static final int SLOT = 30;
    private float mLastX;
    private float mLastY;
    private float mDownX;
    private float mDownY;
    private boolean mActive;
    public UniqueView(Context context) {
        super(context);
    }

    public UniqueView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UniqueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "Touch Down");
                mLastX = mDownX = x;
                mLastY = mDownY = y;
                mActive = false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Touch Move");
                if (!mActive && Math.abs(mDownX - x) > SLOT) {
                    requestDisallowInterceptTouchEvent(true);
                    mActive = true;
                }
                if (mActive) {
                    scroll(x);
                }
                break;
        }
        return true;
    }

    private void scroll(float x) {
        int delta = (int) (mLastX - x);
        if (getScrollX() + delta < 0) {
            scrollTo(0, 0);
        } else if (getScrollX() + delta > convertDpToPixel(120) ) {
            scrollTo((int) convertDpToPixel(120), 0);
        } else {
            scrollBy(delta, 0);
        }
    }


    private float convertDpToPixel(float dp){
        Resources resources = getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
