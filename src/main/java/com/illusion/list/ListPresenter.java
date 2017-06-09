package com.illusion.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.illusion.helpers.ResultListener;
import com.illusion.viper.SuperInteractor;
import com.illusion.viper.SuperPresenter;

/**
 * Created by rammstein on 27.07.16.
 */
public abstract class ListPresenter<
        Entity,
        Adapter extends RecyclerView.Adapter,
        Interactor extends SuperInteractor<Entity>,
        View extends ListFragmentView<Adapter>,
        Router>
        extends SuperPresenter<View, Router> {

    protected ResultListener mResultListener = new ResultListener() {
        @Override
        public void onSuccessResult() {
            if (getView() != null) {
                if (getView().hasData()) {
                    getView().notifyAdapter();
                } else {
                    onViewReady();
                }
            }
        }

        @Override
        public void onFailureResult(String error) {
            if (getView() != null) {
                if (!TextUtils.isEmpty(error))
                    getView().showMessage(error);
            }
        }
    };

    @Override
    public void onDestroy() {
        getInteractor().onDestroy();
        super.onDestroy();
    }

    public abstract Interactor getInteractor();

    public abstract Adapter createAdapter();

    protected boolean hasData() {
        return getInteractor().hasData();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {
        getInteractor().loadData(bundle, mResultListener);
    }

    @Override
    public void onViewReady() {
        if (getView() != null && hasData()) {
            getView().setupList(createAdapter());
        }
    }
}
