package com.illusion.view.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.illusion.viper.SuperFragment;

/**
 * Created by rammstein on 27.07.16.
 */
public abstract class BaseFragment extends SuperFragment implements
        BaseFragmentView {

    @Override
    public void showMessage(@StringRes int message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        if (getView() != null && !TextUtils.isEmpty(message)) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public <Action extends View.OnClickListener> void showActionMessage(String message,
                                                                        @StringRes int actionTitle,
                                                                        @NonNull Action action) {
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
        snackbar.setAction(actionTitle, action);
        snackbar.show();
    }

    @Override
    public <Action extends View.OnClickListener> void showActionMessage(@StringRes int message,
                                                                        @StringRes int actionTitle,
                                                                        @NonNull Action action) {
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
        snackbar.setAction(actionTitle, action);
        snackbar.show();
    }
}
