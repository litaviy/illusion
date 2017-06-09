package com.illusion.view.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.illusion.list.ListFragment;

/**
 * Created by rammstein on 27.07.16.
 */
public abstract class GridListFragment<Adapret extends RecyclerView.Adapter> extends ListFragment<Adapret> {
    @NonNull
    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), getSpanCount());
    }

    public abstract int getSpanCount();
}
