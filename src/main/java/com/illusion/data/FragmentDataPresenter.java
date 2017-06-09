package com.illusion.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.illusion.helpers.ResultListener;
import com.illusion.viper.SuperPresenter;

/**
 * Created by klitaviy on 11/14/16.
 */

public abstract class FragmentDataPresenter<View extends ReadinessListener, Router extends DataLoadingView> extends
        BasePresenter<View, Router> {

    private ResultListener mDataLoadingListener, mBundleListener;

    /**
     * Constructor.
     *
     * @param view - View
     */
    public FragmentDataPresenter(@NonNull final View view, @NonNull final Router router) {
        super(view, router);
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
                FragmentDataPresenter.this.onFailureResult(error);
            }
        };
        mBundleListener = new ResultListener() {
            @Override
            public void onSuccessResult() {
                getDataInteractor().loadData(mDataLoadingListener);
            }

            @Override
            public void onFailureResult(final String error) {
                FragmentDataPresenter.this.onFailureResult(error);
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
