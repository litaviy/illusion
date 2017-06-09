package com.illusion.view.elements;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.illusion.R;


/**
 * Created by rammstein on 28.07.16.
 */
public class ProgressButton extends RelativeLayout {

    private static final int END = 1;
    private static final int START = 2;
    private static final int CENTER = 3;
    protected ProgressBar mProgressBar;
    protected TextView mTitle;
    private CharSequence mTitleText;
    @Dimension
    private float mTitleSize;
    @Dimension
    private int mTitleColor;
    @ColorRes
    private int mProgressColor;
    private boolean mTitleCaps;
    @Dimension
    private float mProgressSize;
    private int mProgressGravity;

    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setLayoutTransition(new LayoutTransition());

        getDataFromAttrs(context, attrs, 0);

        generateProgress(context, android.R.attr.progressBarStyleSmall);
        generateTitle(context);

        generateView();
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setLayoutTransition(new LayoutTransition());

        getDataFromAttrs(context, attrs, defStyleAttr);

        generateProgress(context, defStyleAttr);
        generateTitle(context);

        generateView();
    }

    private void getDataFromAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        getProgressSizeFromAttrs(context, attrs, defStyleAttr);
        getProgressGravityFromAttrs(context, attrs, defStyleAttr);
        getTitleTextFromAttrs(context, attrs, defStyleAttr);
        getTitleSizeFromAttrs(context, attrs, defStyleAttr);
        getTitleColorFromAttrs(context, attrs, defStyleAttr);
    }

    private void generateView() {
        addView(mTitle);
        addView(mProgressBar);
        mProgressBar.setVisibility(GONE);
    }

    private TypedArray getTypedArray(@NonNull Context context,
                                     @NonNull AttributeSet attrs,
                                     int defStyleAttr) {

        return context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ProgressButton,
                defStyleAttr, 0);
    }

    private void getProgressSizeFromAttrs(@NonNull Context context,
                                          @NonNull AttributeSet attrs,
                                          int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mProgressSize = a.getDimension(R.styleable.ProgressButton_progressSize, 10f);
        } finally {
            a.recycle();
        }
    }

    private void getProgressGravityFromAttrs(@NonNull Context context,
                                             @NonNull AttributeSet attrs,
                                             int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mProgressGravity = a.getInteger(R.styleable.ProgressButton_progressGravity, CENTER);
        } finally {
            a.recycle();
        }
    }

    private void getTitleTextFromAttrs(@NonNull Context context,
                                       @NonNull AttributeSet attrs,
                                       int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mTitleText = a.getText(R.styleable.ProgressButton_text);
            mTitleCaps = a.getBoolean(R.styleable.ProgressButton_textCaps, true);
        } finally {
            a.recycle();
        }
    }

    private void getTitleSizeFromAttrs(@NonNull Context context,
                                       @NonNull AttributeSet attrs,
                                       int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mTitleSize = a.getDimensionPixelSize(R.styleable.ProgressButton_textSize, 15);
        } finally {
            a.recycle();
        }
    }

    private void getTitleColorFromAttrs(@NonNull Context context,
                                        @NonNull AttributeSet attrs,
                                        int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mTitleColor = a.getColor(R.styleable.ProgressButton_textColor, Color.BLACK);
            mProgressColor = a.getResourceId(R.styleable.ProgressButton_textColor, Color.BLACK);
        } finally {
            a.recycle();
        }
    }

    private void generateProgress(@NonNull Context context, int defStyleAttr) {
        mProgressBar = new ProgressBar(context, null, defStyleAttr);
        LayoutParams params = new LayoutParams(
                (int) mProgressSize, (int) mProgressSize);
        params.addRule(CENTER_VERTICAL);

        if (mProgressGravity == END) {
            params.addRule(ALIGN_PARENT_END);
        } else if (mProgressGravity == START) {
            params.addRule(ALIGN_PARENT_START);
        } else if (mProgressGravity == CENTER) {
            params.addRule(CENTER_HORIZONTAL);
        }
        mProgressBar.setLayoutParams(params);
    }

    private void generateTitle(@NonNull Context context) {
        mTitle = new TextView(context);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        mTitle.setTextColor(mTitleColor);
        mTitle.setAllCaps(mTitleCaps);

        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(CENTER_IN_PARENT);
        mTitle.setLayoutParams(params);

        if (mTitleText != null) {
            setTitle(mTitleText.toString());
        }
    }


    public void startProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        if (mProgressGravity == CENTER) {
            mTitle.setVisibility(GONE);
        }
    }

    public void stopProgress() {
        if (mProgressGravity == CENTER) {
            mTitle.setVisibility(VISIBLE);
        }
        mProgressBar.setVisibility(View.GONE);
    }

    public void setTitleTypeFace(@NonNull Typeface typeFace) {
        mTitle.setTypeface(typeFace);
    }

    public void setTitle(@StringRes int title) {
        mTitle.setText(title);
    }

    public void setTitle(@Nullable String title) {
        mTitle.setText(title);
    }
}
