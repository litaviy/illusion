package com.illusion.data;

import android.support.annotation.NonNull;

import com.illusion.viper.SuperPresenter;

/**
 * Created by klitaviy on 12/22/16.
 */

public abstract class BasePresenter<View, Router> extends SuperPresenter<View, Router> {
    /**
     * Constructor.
     *
     * @param view   - View
     * @param router - TestRouter
     */
    public BasePresenter(@NonNull final View view, @NonNull final Router router) {
        super();
        setView(view);
        setRouter(router);
    }
}
