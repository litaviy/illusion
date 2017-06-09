package com.illusion.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.illusion.helpers.DataPresentsListener;
import com.illusion.helpers.OnDestroyListener;
import com.illusion.helpers.ResultListener;

/**
 * Created by klitaviy on 11/14/16.
 */

public interface IDataInteractor extends DataPresentsListener,
        OnDestroyListener, ReadinessListener {
    void checkData(@NonNull final ResultListener listener,
                   @NonNull final Bundle data);

    void loadData(@NonNull final ResultListener listener);
}
