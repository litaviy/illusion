package com.illusion.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.illusion.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by numezmat on 03.08.16.
 */
public class Support {

    public static void makeSnackbar(Context context, View view, String message) {
        if (view == null) {
            return;
        }
        getSnackbar(context, view, message, Snackbar.LENGTH_SHORT).show();
    }

    private static Snackbar getSnackbar(Context context,
                                        View view,
                                        String message,
                                        int length) {
        Snackbar snackbar = Snackbar.make(
                view,
                message,
                length
        );
        (snackbar.getView()).setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.colorPrimary));

        return snackbar;
    }

    public static void makeSnackbar(Context context,
                                    View view,
                                    String message,
                                    String actionTitle,
                                    final Runnable action) {

        if (view == null) {
            return;
        }

        Snackbar snackbar = getSnackbar(context, view, message, Snackbar.LENGTH_LONG);

        snackbar.setAction(actionTitle, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.run();
            }
        });
        snackbar.setActionTextColor(Color.CYAN);
        snackbar.show();
    }

    public static void hidenKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isEmailValid(String email) {
        String regExpn = "^[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    public static boolean isGalleryPermission(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean isWritePermission(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (
                ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean isCameraPermission(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean isGPSPermission(Context context) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int icon) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = VectorDrawableCompat.create(
                    context.getResources(),
                    icon,
                    context.getTheme()
            );
        } else {
            drawable = ContextCompat.getDrawable(
                    context, icon
            );
        }

        return drawable;
    }
}
