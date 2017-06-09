package com.illusion.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.illusion.helpers.ResultListener;

/**
 * Created by klitaviy on 11/14/16.
 */

public abstract class RouterDataPresenter<View extends ReadinessListener & DataLoadingView> extends
        BasePresenter<View, View> {

    private ResultListener mDataLoadingListener, mBundleListener;

    /**
     * Constructor.
     *
     * @param view - View
     */
    public RouterDataPresenter(@NonNull final View view) {
        super(view, view);
        mDataLoadingListener = new ResultListener() {
            @Override
            public void onSuccessResult() {
                if (getView().isReady()) {
                    setupView();
                    getRouter().hideProgress();
                }
            }

            @Override
            public void onFailureResult(final String error) {
                RouterDataPresenter.this.onFailureResult(error);
            }
        };
        mBundleListener = new ResultListener() {
            @Override
            public void onSuccessResult() {
                getDataInteractor().loadData(mDataLoadingListener);
            }

            @Override
            public void onFailureResult(final String error) {
                RouterDataPresenter.this.onFailureResult(error);
            }
        };
    }

    @Override
    public void onStart(@Nullable final Bundle data) {
        getRouter().showProgress();
        getDataInteractor().checkData(mBundleListener, data);
    }

    @Override
    public void onViewReady() {
        if (getDataInteractor().isReady()) {
            setupView();
            getRouter().hideProgress();
        }
    }

    @NonNull
    protected abstract IDataInteractor getDataInteractor();

    protected abstract void setupView();

    protected void onFailureResult(@NonNull final String error) {
        getRouter().hideProgress();
        getRouter().showMessage(error);
    }

    @Override
    public void onDestroy() {
        mDataLoadingListener = null;
        mBundleListener = null;
        super.onDestroy();
    }
}
