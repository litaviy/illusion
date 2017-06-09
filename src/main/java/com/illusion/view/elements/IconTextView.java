package com.illusion.view.elements;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.illusion.R;

/**
 * Created by rammstein on 28.07.16.
 */
public class IconTextView extends LinearLayout {

    public static final int END = 1;
    public static final int START = 2;

    protected ImageView mIcon;
    protected TextView mTitle;
    private CharSequence mTitleText;
    @Dimension
    private float mTitleSize;
    @Dimension
    private int mTitleColor;
    @Dimension
    private float mIconSizeDp;
    private float mIconSize;
    @Dimension
    private float mIconMargin;
    @DrawableRes
    private int mIconDrawableRes;
    private Drawable mIconDrawable;
    private int mIconGravity;

    public IconTextView(Context context) {
        super(context);
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER);

        getDataFromAttrs(context, attrs, 0);

        generateIcon(context);
        generateTitle(context);

        generateView();
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER);

        getDataFromAttrs(context, attrs, defStyleAttr);

        generateIcon(context);
        generateTitle(context);

        generateView();
    }

    private void getDataFromAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        getIconSizeFromAttrs(context, attrs, defStyleAttr);
        getIconDrawableFromAttrs(context, attrs, defStyleAttr);
        getIconGravityFromAttrs(context, attrs, defStyleAttr);
        getIconMarginFromAttrs(context, attrs, defStyleAttr);
        getTitleTextFromAttrs(context, attrs, defStyleAttr);
        getTitleSizeFromAttrs(context, attrs, defStyleAttr);
        getTitleColorFromAttrs(context, attrs, defStyleAttr);
    }

    private void generateView() {
        switch (mIconGravity) {
            case END:
                addView(mTitle);
                addView(mIcon);
                break;
            case START:
                addView(mIcon);
                addView(mTitle);
                break;
            default:
                addView(mIcon);
                addView(mTitle);
                break;
        }
    }

    private TypedArray getTypedArray(@NonNull Context context,
                                     @NonNull AttributeSet attrs,
                                     int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IconTextView,
                defStyleAttr, 0);

        return a;
    }

    private void getIconSizeFromAttrs(@NonNull Context context,
                                      @NonNull AttributeSet attrs,
                                      int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mIconSizeDp = a.getDimension(R.styleable.IconTextView_iconSize, 10f);
        } finally {
            a.recycle();
        }
    }

    private void getIconDrawableFromAttrs(@NonNull Context context,
                                          @NonNull AttributeSet attrs,
                                          int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mIconDrawableRes = a.getResourceId(R.styleable.IconTextView_iconSource, 0);
        } finally {
            a.recycle();
        }
    }

    private void getIconGravityFromAttrs(@NonNull Context context,
                                         @NonNull AttributeSet attrs,
                                         int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mIconGravity = a.getInteger(R.styleable.IconTextView_iconGravity, 0);
        } finally {
            a.recycle();
        }
    }

    private void getIconMarginFromAttrs(@NonNull Context context,
                                        @NonNull AttributeSet attrs,
                                        int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mIconMargin = a.getDimension(R.styleable.IconTextView_iconMargin, 5);
        } finally {
            a.recycle();
        }
    }

    private void getTitleTextFromAttrs(@NonNull Context context,
                                       @NonNull AttributeSet attrs,
                                       int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IconTextView,
                defStyleAttr, 0);
        try {
            mTitleText = a.getText(R.styleable.IconTextView_titleText);
        } finally {
            a.recycle();
        }
    }

    private void getTitleSizeFromAttrs(@NonNull Context context,
                                       @NonNull AttributeSet attrs,
                                       int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IconTextView,
                defStyleAttr, 0);
        try {
            mTitleSize = a.getDimensionPixelSize(R.styleable.IconTextView_titleSize, 15);
        } finally {
            a.recycle();
        }
    }

    private void getTitleColorFromAttrs(@NonNull Context context,
                                        @NonNull AttributeSet attrs,
                                        int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IconTextView,
                defStyleAttr, 0);
        try {
            mTitleColor = a.getColor(R.styleable.IconTextView_titleColor, Color.BLACK);
        } finally {
            a.recycle();
        }
    }

    private void generateIcon(@NonNull Context context) {
        mIcon = new ImageView(context);

//        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIconSize, getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(
                (int) mIconSizeDp, (int) mIconSizeDp);
        if (mIconGravity == END) {
            params.setMarginStart((int) mIconMargin);
        } else if (mIconGravity == START) {
            params.setMarginEnd((int) mIconMargin);
        }
        mIcon.setLayoutParams(params);

        if (mIconDrawableRes > 0) {
            setIcon(mIconDrawableRes);
        }
    }

    private void generateTitle(@NonNull Context context) {
        mTitle = new TextView(context);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        mTitle.setTextColor(mTitleColor);
        if (mTitleText != null) {
            setTitle(mTitleText.toString());
        }
    }

    public void setIconSize(@Dimension float iconSize) {
        mIconSize = iconSize;
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIconSize, getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(
                size, size);
        if (mIconGravity == END) {
            params.setMarginStart((int) mIconMargin);
        } else if (mIconGravity == START) {
            params.setMarginEnd((int) mIconMargin);
        }
        if (mIcon != null) {
            mIcon.setLayoutParams(params);
            if (mIconDrawableRes > 0) {
                setIcon(mIconDrawableRes);
            }
            if (mIconDrawable != null) {
                setIcon(mIconDrawable);
            }
        }
    }


    public void setTitleTypeFace(@NonNull Typeface typeFace) {
        mTitle.setTypeface(typeFace);
    }

    public void setIcon(@DrawableRes int icon) {
        mIcon.setImageResource(icon);
    }

    public void setIcon(Drawable icon) {
        mIconDrawable = icon;
        mIcon.setImageDrawable(mIconDrawable);
    }

    public void setTitle(@StringRes int title) {
        mTitle.setText(title);
    }

    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    public void setTitleColor(ColorStateList colors) {
        mTitle.setTextColor(colors);
    }

    public void setTitle(@Nullable String title) {
        mTitle.setText(title);
    }

    public void setIconGravity(int iconGravity) {
        mIconGravity = iconGravity;
    }

    public void build(@DrawableRes int icon, @StringRes int title) {
        setIcon(icon);
        setTitle(title);
    }

    public void build(@NonNull Drawable icon, @Nullable String title) {
        setIcon(icon);
        setTitle(title);
    }

    public void build(@DrawableRes int icon, @Nullable String title) {
        setIcon(icon);
        setTitle(title);
    }

    public void build(@NonNull Drawable icon, @StringRes int title) {
        setIcon(icon);
        setTitle(title);
    }
}
