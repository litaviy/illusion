package com.illusion.view.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

/**
 * Created by rammstein on 27.07.16.
 */
public interface BaseFragmentView {
    void showMessage(@StringRes int message);

    void showMessage(@NonNull String message);

    <Action extends View.OnClickListener> void showActionMessage(String message,
                                                                 @StringRes int actionTitle,
                                                                 @NonNull Action action);

    <Action extends View.OnClickListener> void showActionMessage(@StringRes int message,
                                                                 @StringRes int actionTitle,
                                                                 @NonNull Action action);
}
