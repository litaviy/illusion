package com.illusion.list.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by rammstein on 29.07.16.
 */
public abstract class ListAdapter<Entity extends Identifier, Holder extends RecyclerView.ViewHolder, Listener extends ListAdapterListener<Entity>>
        extends RecyclerView.Adapter<Holder> {

    protected Listener mListener;
    protected Entity mEntity;

    public ListAdapter(Listener listener) {
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mEntity = mListener.getItem(position);
    }

    @Override
    public int getItemCount() {
        return mListener.getItemCount();
    }

    @Override
    public long getItemId(int position) {
        return mListener.getItem(position).getId();
    }
}
