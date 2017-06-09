package com.illusion.helpers;

import android.support.annotation.IntDef;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by rammstein on 28.07.16.
 */
@IntDef({Gravity.LEFT, Gravity.RIGHT, GravityCompat.START, GravityCompat.END})
@Retention(RetentionPolicy.SOURCE)
public @interface DrawerGravity {
}