package com.illusion.list.adapter;

import android.support.annotation.NonNull;

/**
 * Created by rammstein on 29.07.16.
 */
public interface ListAdapterListener<Entity> {

    @NonNull
    Entity getItem(int position);

    int getItemCount();

    void onItemClick(int position);
}
