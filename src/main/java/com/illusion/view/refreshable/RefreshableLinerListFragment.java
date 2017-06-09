package com.illusion.view.refreshable;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.illusion.R;
import com.illusion.helpers.ResultListener;
import com.illusion.list.RefreshableListPresenter;
import com.illusion.view.list.LinearListFragment;

/**
 * Created by numezmat on 28.07.16.
 */
public abstract class RefreshableLinerListFragment<
        Adapter extends RecyclerView.Adapter,
        Presenter extends RefreshableListPresenter> extends LinearListFragment<Adapter> {

    protected Presenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @IdRes
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_recycler_parent;
    }

    @Override
    public int getRecyclerListId() {
        return R.id.recycler_list;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.destroyDrawingCache();
            mSwipeRefreshLayout.clearAnimation();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(getSwipeRefreshLayoutId());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    protected void setColorShemSwipe(@ColorRes @NonNull int... colorResId) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResId);
    }

    private void refreshData() {
        if (mPresenter == null) {
            throw new NullPointerException("Presenter must be initialized!");
        }
        mPresenter.reLoadData(new ResultListener() {
            @Override
            public void onSuccessResult() {
                onRefreshSuccess();
            }

            @Override
            public void onFailureResult(String error) {
                if (!TextUtils.isEmpty(error))
                    showMessage(error);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void onRefreshSuccess() {
        notifyAdapter();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
