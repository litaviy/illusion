package com.illusion.view.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.illusion.helpers.DrawerGravity;
import com.illusion.helpers.Layout;

/**
 * Created by rammstein on 27.07.16.
 */
public abstract class DrawerActivity extends Activity {

    protected DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mDrawerLayout = (DrawerLayout) findViewById(getDrawerId());

        // SETUP CHILD VIEW
        Class cls = getClass();
        if (!cls.isAnnotationPresent(Layout.class)) {
            throw new IllegalStateException("Child class must have @Layout annotation!");
        }

        Layout layout = (Layout) cls.getAnnotation(Layout.class);
        ((ViewGroup) findViewById(getContainerId())).addView(
                LayoutInflater.from(this).inflate(layout.id(), null)
        );
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(getDrawerGravity())) {
            mDrawerLayout.closeDrawer(getDrawerGravity());
        } else {
            super.onBackPressed();
        }
    }

    /**
     * @return gravity of drawer that should be
     */
    @DrawerGravity
    public abstract int getDrawerGravity();

    /**
     * Id of view that must include drawer(getDrawerId())
     * and container(getContainerId()) where will be added @Layout(id) view
     *
     * @return resource identifier
     */
    @LayoutRes
    protected abstract int getContentView();

    @IdRes
    protected abstract int getDrawerId();

    /**
     * Id of container where will be added @Layout(id) view
     * @return resource identifier of container
     */
    @IdRes
    protected abstract int getContainerId();

}
