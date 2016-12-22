package com.example.pcts.bustracker.CustomComponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

/**
 * Created by Ricardo Morais on 22/12/2016.
 */

public class ImageViewCheckable extends ImageView implements Checkable {

    private boolean isChecked = true;

    public ImageViewCheckable(Context context) {
        super(context);
    }

    public ImageViewCheckable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public ImageViewCheckable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setChecked(boolean b) {
        isChecked = b;

        if(b){
            this.setVisibility(VISIBLE);
        } else {
            this.setVisibility(GONE);
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
