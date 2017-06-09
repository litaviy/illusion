package com.illusion.view.base;

import com.illusion.helpers.ResultListener;
import com.illusion.viper.SuperPresenter;

/**
 * Created by numezmat on 28.07.16.
 */
public abstract class BasePresenter<View, Router> extends SuperPresenter<View, Router> {

    public abstract void reLoadData(ResultListener listener);
}
