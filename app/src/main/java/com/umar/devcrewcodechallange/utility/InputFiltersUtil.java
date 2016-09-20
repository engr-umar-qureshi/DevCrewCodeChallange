package com.umar.devcrewcodechallange.utility;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by UmarQasim on 9/20/2016.
 */
public class InputFiltersUtil {

    public static InputFilter getDisplayNameFilter()
    {
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source.length() > 0) {

                    if(!Character.isSpaceChar(source.charAt(source.length()-1))&&!Character.isAlphabetic(source.charAt(source.length()-1))){
                        return source.subSequence(0, source.length()-1);
                    }

                }

                return null;

            }
        };


    }
}
