package com.illusion.view.elements;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.illusion.R;
import com.illusion.helpers.OnHashTagSelectedListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by root on 28.09.16.
 */

public class BetterHashtagTextView extends TextView {

    //    private int spanColor;
    @Dimension
    private int mHashTagColor;
    private OnHashTagSelectedListener mLisener;


    public BetterHashtagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.setMovementMethod(LinkMovementMethod.getInstance());

//        this.setHighlightColor(Color.TRANSPARENT);
        getDataFromAttrs(context, attrs, defStyleAttr);
    }

    public BetterHashtagTextView(final Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setMovementMethod(LinkMovementMethod.getInstance());

//        this.setHighlightColor(Color.TRANSPARENT);
        getDataFromAttrs(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BetterHashtagTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.setMovementMethod(LinkMovementMethod.getInstance());

//        this.setHighlightColor(Color.TRANSPARENT);
        getDataFromAttrs(context, attrs, defStyleAttr);
    }

    private void getDataFromAttrs(Context context, AttributeSet attrs, int defStyleAttr) {

        setHashTagColor(context, attrs, defStyleAttr);

        setText(getSpannableString(this.getText().toString(), false));
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

    public void setTextHashtag(CharSequence texthashtag) {
        setText(getSpannableString(texthashtag.toString(), false));
    }

    public void setTextHashtagColor(@ColorRes int color) {
        mHashTagColor = color;
    }

    private void setHashTagColor(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getTypedArray(context, attrs, defStyleAttr);
        try {
            mHashTagColor = a.getInt(R.styleable.BetterHashtagTextView_hasgtagColor, getCurrentTextColor());
        } finally {
            a.recycle();
        }
    }

    public void setOnHashTagSelectedListener(OnHashTagSelectedListener lisener) {
        mLisener = lisener;
    }

    private SpannableString getSpannableString(String textFromView, boolean isComment) {

        SpannableString spannableString = new SpannableString(textFromView);

        Matcher matcherHash = Pattern.compile("#([A-Za-z0-9_-]+\\S{0,1})").matcher(spannableString);
        Matcher matcherMent = Pattern.compile("@([A-Za-z0-9_.-]+\\S{0,1})").matcher(spannableString);
        Matcher matcherLink = Pattern.compile("(https?:\\/\\/)?(www\\.)?([a-zA-Z0-9]+)(\\.)([a-z]{2,3})(\\.[a-z]{2,})?+").matcher(spannableString);

        while (matcherHash.find()) {
            final String spanText = textFromView.substring(matcherHash.start(), matcherHash.end());

            spannableString.setSpan(
                    new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            if (mLisener != null) {
                                mLisener.onClick(spanText);
                            }
//                            Toast.makeText(PuVoiceNewApp.getAppContext(), spanText, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                        }
                    },
                    matcherHash.start(),
                    matcherHash.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannableString.setSpan(
                    new ForegroundColorSpan(ContextCompat.getColor(getContext(), mHashTagColor)),
                    matcherHash.start(),
                    matcherHash.end(),
                    0);
        }

        while (matcherMent.find()) {
            final String spanText = textFromView.substring(matcherMent.start(), matcherMent.end());

            spannableString.setSpan(
                    new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            if (mLisener != null) {
                                mLisener.onClick(spanText);
                            }
//                            Toast.makeText(PuVoiceNewApp.getAppContext(), spanText, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                        }
                    },
                    matcherMent.start(),
                    matcherMent.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannableString.setSpan(
                    new ForegroundColorSpan(ContextCompat.getColor(getContext(), mHashTagColor)),
                    matcherMent.start(),
                    matcherMent.end(),
                    0);
        }
        while (matcherLink.find()) {
            final String spanText = textFromView.substring(matcherLink.start(), matcherLink.end());

            spannableString.setSpan(
                    new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            if (mLisener != null) {
                                mLisener.onClick(spanText);
                            }
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                        }
                    },
                    matcherLink.start(),
                    matcherLink.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannableString.setSpan(
                    new ForegroundColorSpan(ContextCompat.getColor(getContext(), mHashTagColor)),
                    matcherLink.start(),
                    matcherLink.end(),
                    0);
        }
        return spannableString;
    }
}
