package com.illusion.data;

import android.support.annotation.NonNull;

/**
 * Created by rammstein on 11.11.16.
 */

public interface SuperView {

    void showMessage(@NonNull final String message);

    void showActionMessage(@NonNull final String message,
                           @NonNull final String actName,
                           @NonNull final Runnable action);
}
