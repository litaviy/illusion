package com.illusion.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.illusion.helpers.ResultListener;
import com.illusion.list.adapter.Identifier;
import com.illusion.list.adapter.ListAdapter;
import com.illusion.list.adapter.ListAdapterListener;
import com.illusion.viper.SuperInteractor;
import com.illusion.viper.SuperPresenter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by rammstein on 01.08.16.
 */
public abstract class ListPresenterNext<
        Entity extends Identifier,
        Holder extends RecyclerView.ViewHolder,
        ListListener extends ListAdapterListener<Entity>,
        Adapter extends ListAdapter<Entity, Holder, ListListener>,
        Interactor extends SuperInteractor<List<Entity>>,
        View extends ListFragmentView<Adapter>,
        Router>
        extends SuperPresenter<View, Router> {

    protected Interactor mInteractor;

    protected ListAdapterListener<Entity> mListAdapterListener = new ListAdapterListener<Entity>() {
        @Override
        public Entity getItem(int position) {
            return mInteractor.getData().get(position);
        }

        @Override
        public int getItemCount() {
            return mInteractor.getData().size();
        }

        @Override
        public void onItemClick(int position) {

        }
    };
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

    public abstract Class<Adapter> getAdapterType();

    @Override
    public void onDestroy() {
        mInteractor.onDestroy();
        super.onDestroy();
    }

    protected boolean hasData() {
        return mInteractor.hasData();
    }

    @Override
    public void onStart(@Nullable Bundle bundle) {
        mInteractor.loadData(bundle, mResultListener);
    }

    @Override
    public void onViewReady() {
        if (getView() != null &&
                mInteractor != null &&
                hasData()) {
            try {
                getView().setupList(getAdapterType()
                        .getDeclaredConstructor(ListAdapterListener.class)
                        .newInstance(mListAdapterListener));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}