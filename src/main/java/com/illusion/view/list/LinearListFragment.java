package com.illusion.view.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.illusion.list.ListFragment;

/**
 * Created by rammstein on 27.07.16.
 */
public abstract class LinearListFragment<Adapter extends RecyclerView.Adapter> extends ListFragment<Adapter> {
    @NonNull
    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
    }
}
