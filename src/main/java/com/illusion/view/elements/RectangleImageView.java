package com.illusion.view.elements;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.illusion.R;

/**
 * Created by numezmat on 28.07.16.
 */
public class RectangleImageView extends ImageView {

    private final int ONE = 1, TWO = 2, TREE = 3, FOUR = 4;

    private int mPartsOfWidth;
    private ImageView.ScaleType mScaleType = ScaleType.CENTER_CROP;

    public RectangleImageView(Context context) {
        super(context);
    }

    public RectangleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RectangleImageView,
                0, 0);
        try {
            mPartsOfWidth = a.getInteger(R.styleable.RectangleImageView_partsOfWidth, ONE);
        } finally {
            a.recycle();
        }
    }

    public RectangleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RectangleImageView,
                defStyleAttr, 0);
        try {
            mPartsOfWidth = a.getInteger(R.styleable.RectangleImageView_partsOfWidth, ONE);
        } finally {
            a.recycle();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RectangleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RectangleImageView,
                defStyleAttr, defStyleRes);
        try {
            mPartsOfWidth = a.getInteger(R.styleable.RectangleImageView_partsOfWidth, ONE);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setScaleType(ImageView.ScaleType scaleType) {
        mScaleType = scaleType;
        super.setScaleType(mScaleType);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        switch (mPartsOfWidth) {
            case ONE:
                heightMeasureSpec = widthMeasureSpec / 4;
                break;
            case TWO:
                heightMeasureSpec = widthMeasureSpec / 2;
                break;
            case TREE:
                heightMeasureSpec = (int) (widthMeasureSpec * 0.75);
                break;
            case FOUR:
                heightMeasureSpec = widthMeasureSpec;
                break;
            default:
                heightMeasureSpec = widthMeasureSpec;
                break;
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        setScaleType(mScaleType);
    }
}
