package com.illusion.list;

import android.support.v7.widget.RecyclerView;

import com.illusion.helpers.ResultListener;
import com.illusion.viper.SuperInteractor;

/**
 * Created by numezmat on 28.07.16.
 */
public abstract class RefreshableListPresenter<
        Entity,
        Adapter extends RecyclerView.Adapter,
        Interactor extends SuperInteractor<Entity>,
        View extends ListFragmentView<Adapter>,
        Router>
        extends ListPresenter<Entity, Adapter, Interactor, View, Router> {

    public abstract void reLoadData(ResultListener listener);
}
