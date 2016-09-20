package com.umar.devcrewcodechallange.utility;

/**
 * Created by UmarQasim on 9/20/2016.
 */
public class WordUtil {

    public static String capitalizeFully(String input)
    {
        String output = input.toLowerCase();
        return Character.toUpperCase(output.charAt(0)) + output.substring(1);
    }
}
