package com.illusion.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.illusion.helpers.Layout;

/**
 * Created by rammstein on 27.07.16.
 */
public class SuperActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class cls = getClass();
        if (!cls.isAnnotationPresent(Layout.class)) {
            return;
        }

        Layout layout = (Layout) cls.getAnnotation(Layout.class);
        setContentView(layout.id());
    }
}
