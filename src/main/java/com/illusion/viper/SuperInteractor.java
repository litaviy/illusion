package com.illusion.viper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.illusion.helpers.DataPresentsListener;
import com.illusion.helpers.OnDestroyListener;
import com.illusion.helpers.ResultListener;

/**
 * Created by rammstein on 27.07.16.
 */
public abstract class SuperInteractor<Entity> implements
        DataPresentsListener,
        OnDestroyListener {

    protected Entity mData;

    public abstract void loadData(@Nullable Bundle bundle,
                                  @NonNull ResultListener listener);

    @Nullable
    public Entity getData() {
        return mData;
    }

    @Override
    public boolean hasData() {
        return getData() != null;
    }

    @Override
    public void onDestroy() {
        mData = null;
    }
}
