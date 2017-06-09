package com.illusion.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.illusion.helpers.DataPresentsListener;
import com.illusion.view.base.BaseFragmentView;

/**
 * Created by rammstein on 27.07.16.
 */
public interface ListFragmentView<Adapter extends RecyclerView.Adapter> extends
        BaseFragmentView,
        DataPresentsListener {

    void setupList(@NonNull Adapter adapter);

    void notifyAdapter();

    void notifyAdapterItem(int position);
}
