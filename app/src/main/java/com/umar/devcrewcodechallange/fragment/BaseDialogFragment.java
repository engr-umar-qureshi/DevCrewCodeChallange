package com.umar.devcrewcodechallange.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.umar.devcrewcodechallange.utility.InjectView;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by UmarQasim on 9/20/2016.
 */
public class BaseDialogFragment extends DialogFragment {
    private static final String TAG = BaseDialogFragment.class.getSimpleName();

    public View setFragmentContentViews(LayoutInflater inflater, ViewGroup container, boolean attachToRoot, int resId) {
        View view = inflater.inflate(resId, container, attachToRoot);
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            InjectView annos = field.getAnnotation(InjectView.class);
            if (annos != null) {
                try {
                    field.setAccessible(true);
                    View v = view.findViewById(annos.value());
                    if (v != null) {
                        field.set(this, (field.getType().cast(v)));
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception setting fragment content views", e);
                }
            }
        }
        return view;
    }



}