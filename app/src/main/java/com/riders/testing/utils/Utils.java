package com.riders.testing.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.riders.testing.R;

import java.util.Random;

/**
 * Created by Michaël on 07/03/2017.
 */

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    /** This class can't be instantiated. */
    private Utils(){}

    private static String[] urls_thumb = {
            "http://a5.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY5NjUxODQzNTUw.jpg",
            "http://a4.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY4NTc4MzEwMTU0.jpg",
            "http://a5.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY4MDQxMjI5NTg2.jpg",
            "http://a2.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY3MjM2MTg2NTkw.jpg",
            "http://a2.files.theultralinx.com/image/upload/c_fit,cs_srgb,w_620/MTI5MDI0MjY2NDMwNjgyMzg2.png",
            "http://a3.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY1NjI1MzIzNTMw.jpg",
            "http://a3.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY0ODIwMDA0MTE0.jpg",
            "http://a2.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjY0MDE0NjcwNDY3.jpg",
            "http://a3.files.theultralinx.com/image/upload/c_fit,cs_srgb,w_620/MTI5MDI0MjYzMjA5MzkyNjA2.png",
            "http://a3.files.theultralinx.com/image/upload/c_fit,cs_srgb,dpr_1.0,q_80,w_620/MTI5MDI0MjYyNDA0MjM5MzMw.jpg"
    };

    public static String RandomUrl(){

        int idx = new Random().nextInt(urls_thumb.length);
        String randomImg = (urls_thumb[idx]);

        Log.e(TAG, "--- Class Utils --- : Return Random url | " + randomImg);
        return randomImg;
    }


    public static  void showActionInToast(Context context, String textToShow){
        Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
    }

    public static void showActionInSnackBar(Context context, View view, String message, SnackBarType type){
        // create instance
        Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);

        /*switch (type){
            case NORMAL:
                // set action button color
                snackBar.setActionTextColor(context.getResources().getColor(R.color.indigo));
                break;

            case WARNING:
                snackBar.setActionTextColor(context.getResources().getColor(R.color.indigo));
                break;

            case ALERT:
                snackBar.setActionTextColor(context.getResources().getColor(R.color.indigo));
                break;
        }*/

        // get snackBar view
        View snackBarView = snackBar.getView();

        // change snackbar text color
        int snackBarTextId = android.support.design.R.id.snackbar_text;
        TextView textView = (TextView) snackBarView.findViewById(snackBarTextId);
        switch (type){
            case NORMAL:
                // set action button color
                textView.setTextColor(context.getResources().getColor(R.color.white));
                break;

            case WARNING:
                textView.setTextColor(context.getResources().getColor(R.color.filterListViewColorPrimary));
                break;

            case ALERT:
                textView.setTextColor(context.getResources().getColor(R.color.locationColorPrimary));
                break;
        }

        snackBar.show();

        /*
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setActionTextColor()
                .setAction("Action", null).show();
                */
    }


    public static boolean isSameDomain(String url, String url1) {
        return getRootDomainUrl(url.toLowerCase()).equals(getRootDomainUrl(url1.toLowerCase()));
    }

    private static String getRootDomainUrl(String url) {
        String[] domainKeys = url.split("/")[2].split("\\.");
        int length = domainKeys.length;
        int dummy = domainKeys[0].equals("www") ? 1 : 0;
        if (length - dummy == 2)
            return domainKeys[length - 2] + "." + domainKeys[length - 1];
        else {
            if (domainKeys[length - 1].length() == 2) {
                return domainKeys[length - 3] + "." + domainKeys[length - 2] + "." + domainKeys[length - 1];
            } else {
                return domainKeys[length - 2] + "." + domainKeys[length - 1];
            }
        }
    }

    public static void tintMenuIcon(Context context, MenuItem item, int color) {
        Drawable drawable = item.getIcon();
        if (drawable != null) {
            // If we don't mutate the drawable, then all drawable's with this id will have a color
            // filter applied to it.
            drawable.mutate();
            drawable.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static void bookmarkUrl(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences("androidhive", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        // if url is already bookmarked, unbookmark it
        if (pref.getBoolean(url, false)) {
            editor.putBoolean(url, false);
        } else {
            editor.putBoolean(url, true);
        }

        editor.commit();
    }

    public static boolean isBookmarked(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences("androidhive", 0);
        return pref.getBoolean(url, false);
    }


}
