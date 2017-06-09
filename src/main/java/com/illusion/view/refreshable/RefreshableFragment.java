package com.illusion.view.refreshable;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import com.illusion.helpers.ResultListener;
import com.illusion.view.base.BaseFragment;
import com.illusion.view.base.BasePresenter;

/**
 * Created by numezmat on 28.07.16.
 */
public abstract class RefreshableFragment<Presenter extends BasePresenter>
        extends BaseFragment {

    protected Presenter mPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @IdRes
    protected abstract int getSwipeRefreshLayoutId();

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

    protected abstract void notifyData();

    private void onRefreshSuccess() {
        notifyData();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
