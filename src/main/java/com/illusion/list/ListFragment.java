package com.illusion.list;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.illusion.view.base.BaseFragment;

/**
 * Created by rammstein on 27.07.16.
 */
public abstract class ListFragment<Adapter extends RecyclerView.Adapter>
        extends BaseFragment
        implements ListFragmentView<Adapter> {

    protected Adapter mAdapter;
    protected RecyclerView mRecyclerView;

    @IdRes
    public abstract int getRecyclerListId();

    @NonNull
    public abstract RecyclerView.LayoutManager getLayoutManager();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(getRecyclerListId());
        mRecyclerView.setLayoutManager(getLayoutManager());
    }

    @Override
    public void setupList(@NonNull Adapter adapter) {
        mAdapter = adapter;
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean hasData() {
        return mAdapter != null;
    }

    @Override
    public void notifyAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyAdapterItem(int position) {
        mAdapter.notifyItemChanged(position);
    }
}
