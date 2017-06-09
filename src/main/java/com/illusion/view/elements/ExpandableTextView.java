package com.illusion.view.elements;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.illusion.R;


/**
 * Created by prossik on 27.07.16.
 */

public class ExpandableTextView extends TextView {

    private final int E_TYPE_ICON = 1;
    private final int E_TYPE_TEXT = 2;
    private final int E_TYPE_BOTH = 3;

    private final int E_SIZE_SMALL = 1;
    private final int E_SIZE_MEDIUM = 2;
    private final int E_SIZE_LARGE = 3;

    private final int E_GRAVITY_START = 1;
    private final int E_GRAVITY_CENTER = 2;
    private final int E_GRAVITY_END = 3;

    private final int E_BEHAVIOR_TEXT = 1;
    private final int E_BEHAVIOR_EXPANDER = 2;

    private final int E_NEW_LINE = 1;
    private final int E_INNER_LINE = 2;

    private int DEFAULT_TRIM_LENGTH = 0;
    private String DOTS = "...";
    private String mCloseText;
    private String mMore = " more";
    private String mOpentText;
    private String mLess = " less";
    private String ACTION = mMore;
    private int mExpanderType = 3;
    private int mExpanderSize = 1;

    private String mExpanderOpenIcon = "\u2227";
    private String mExpanderCloseIcon = "\u02ec";

    private String mExpanderOpenIconSmall = "\u2191";
    private String mExpanderOpenIconMedium = "\u21D1";
    private String mExpanderOpenIconLarge = "\u1431";

    private String mExpanderCloseIconSmall = "\u2193";
    private String mExpanderCloseIconMedium = "\u21D3";
    private String mExpanderCloseIconLarge = "\u142F";


    private Spannable originalText;
    private Spannable trimmedText;
    private BufferType bufferType;
    private int mBehavior;
    private int mPosition;
    private int mGravity;
    private boolean trim = true;
    private int trimLength;
    @Dimension
    private int mExpanderOpenTextColor;
    @Dimension
    private int mExpanderCloseTextColor;

    public ExpandableTextView(Context context) {
        super(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getDataFromAttrs(context, attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getDataFromAttrs(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getDataFromAttrs(context, attrs, defStyleAttr);
    }

    private void getDataFromAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        setTrim(context, attrs, defStyleAttr);
        setMore(context, attrs, defStyleAttr);
        setLess(context, attrs, defStyleAttr);
        setExpanderTextColor(context, attrs, defStyleAttr);
        setExpanderSize(context, attrs, defStyleAttr);
        setExpanderType(context, attrs, defStyleAttr);
        setBehavior(context, attrs, defStyleAttr);
        setGravity(context, attrs, defStyleAttr);
        setPosition(context, attrs, defStyleAttr);

        setText(getText(), bufferType);

        if (mBehavior == E_BEHAVIOR_EXPANDER) {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    trim = !trim;
                    setText();
                    requestFocusFromTouch();
                }
            });
        }
    }

    private void setGravity(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mGravity = a.getInt(R.styleable.ExpandableTextView_expanderGravity, E_GRAVITY_CENTER);
        } finally {
            a.recycle();
        }
    }

    private void setExpanderTextColor(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mExpanderOpenTextColor = a.getInt(R.styleable.ExpandableTextView_expanderOpenTextColor, getCurrentTextColor());
            mExpanderCloseTextColor = a.getInt(R.styleable.ExpandableTextView_expanderCloseTextColor, getCurrentTextColor());
        } finally {
            a.recycle();
        }
    }

    private TypedArray getTypedArray(@NonNull Context context,
                                     @NonNull AttributeSet attrs,
                                     int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExpandableTextView,
                defStyleAttr, 0);

        return a;
    }

    private void setTrim(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            trimLength = a.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH);
        } finally {
            a.recycle();
        }
    }

    private void setMore(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mCloseText = a.getString(R.styleable.ExpandableTextView_expanderCloseText);
        } finally {
            a.recycle();
        }
    }

    private void setLess(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mOpentText = a.getString(R.styleable.ExpandableTextView_expanderOpenText);
        } finally {
            a.recycle();
        }
    }

    private void setExpanderSize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mExpanderSize = a.getInt(R.styleable.ExpandableTextView_expanderIconSize, 1);
            switch (mExpanderSize) {
                case E_SIZE_SMALL:
                    mExpanderOpenIcon = mExpanderOpenIconSmall;
                    mExpanderCloseIcon = mExpanderCloseIconSmall;
                    break;
                case E_SIZE_MEDIUM:
                    mExpanderOpenIcon = mExpanderOpenIconMedium;
                    mExpanderCloseIcon = mExpanderCloseIconMedium;
                    break;
                case E_SIZE_LARGE:
                    mExpanderOpenIcon = mExpanderOpenIconLarge;
                    mExpanderCloseIcon = mExpanderCloseIconLarge;
                    break;
                default:
                    break;
            }
        } finally {
            a.recycle();
        }
    }

    private void setExpanderType(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mExpanderType = a.getInt(R.styleable.ExpandableTextView_expanderType, 3);
            switch (mExpanderType) {
                case E_TYPE_ICON:
                    mMore = mExpanderCloseIcon;
                    break;
                case E_TYPE_TEXT:
                    mMore = mCloseText;
                    break;
                case E_TYPE_BOTH:
                    mMore = mCloseText + mExpanderCloseIcon;
                    break;
                default:
                    break;
            }
        } finally {
            a.recycle();
        }
    }

    private void setBehavior(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mBehavior = a.getInt(R.styleable.ExpandableTextView_behavior, E_BEHAVIOR_EXPANDER);
        } finally {
            a.recycle();
        }
    }

    private void setPosition(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mPosition = a.getInt(R.styleable.ExpandableTextView_expanderPosition, E_INNER_LINE);
        } finally {
            a.recycle();
        }
    }

    private void setText() {
        super.setText(new SpannableStringBuilder(getDisplayableText()), bufferType);
    }

    private Spannable getDisplayableText() {
        if (trim) {
            return trimmedText;
        } else {
            return new SpannableStringBuilder(getLessText());
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if ((trimLength > 0) && (text.length() > trimLength)) {
            originalText = new SpannableStringBuilder(text);
            trimmedText = new SpannableStringBuilder(getTrimmedText());
            bufferType = type;
            setText();
        } else {
            bufferType = type;
            super.setText(text, bufferType);
        }
    }

    private CharSequence getTrimmedText() {
        if (originalText != null && originalText.length() > trimLength) {
            ACTION = mMore;
            SpannableStringBuilder builder = new SpannableStringBuilder(originalText, 0, trimLength + 1);
            SpannableString blackSpannable = new SpannableString(DOTS);
            blackSpannable.setSpan(new ForegroundColorSpan(getCurrentTextColor()),
                    0, DOTS.length(), 0);
            builder.append(blackSpannable);

            SpannableString orangeSpannable = new SpannableString(ACTION);
            if (mPosition == E_NEW_LINE) {
                ACTION = "\n" + ACTION;
                orangeSpannable = new SpannableString(ACTION);
                if (mGravity == E_GRAVITY_CENTER) {
                    orangeSpannable.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                            0, ACTION.length(), 0);
                } else if (mGravity == E_GRAVITY_START) {
                    orangeSpannable.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL),
                            0, ACTION.length(), 0);
                } else if (mGravity == E_GRAVITY_END) {
                    orangeSpannable.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
                            0, ACTION.length(), 0);
                }
            }
            orangeSpannable.setSpan(new ForegroundColorSpan(mExpanderCloseTextColor),
                    0, ACTION.length(), 0);
            builder.append(orangeSpannable);

            return builder;
        } else {
            return originalText;
        }
    }

    private CharSequence getLessText() {
        ACTION = mLess + mExpanderOpenIcon;
        SpannableStringBuilder builder = new SpannableStringBuilder(originalText, 0, originalText.length());

        SpannableString orangeSpannable = new SpannableString(ACTION);
        if (mPosition == E_NEW_LINE) {
            ACTION = "\n" + ACTION;
            orangeSpannable = new SpannableString(ACTION);
            if (mGravity == E_GRAVITY_CENTER) {
                orangeSpannable.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0, ACTION.length(), 0);
            } else if (mGravity == E_GRAVITY_START) {
                orangeSpannable.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL),
                        0, ACTION.length(), 0);
            } else if (mGravity == E_GRAVITY_END) {
                orangeSpannable.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
                        0, ACTION.length(), 0);
            }
        }
        orangeSpannable.setSpan(new ForegroundColorSpan(mExpanderOpenTextColor),
                0, ACTION.length(), 0);
        builder.append(orangeSpannable);

        return builder;
    }
}
