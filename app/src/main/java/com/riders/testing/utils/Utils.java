package com.riders.testing.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;
import com.riders.testing.R;
import com.riders.testing.constants.SnackBarType;

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

    /**
     * Hide the keyboard
     * @param view
     */
    public static void hideKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * Show an alertDialog
     * @param
     * @param title
     * @param message
     * @param negativeMessage
     * @param positiveMessage
     */
    public static void showAlertDialog(final Activity activity, final Context context, String title, String message, final String negativeMessage, final String positiveMessage){
        Log.i("Activity - AlertDialog", "Show alert dialog");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setNegativeButton(negativeMessage, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.showActionInToast(context, negativeMessage);
                if(negativeMessage.equalsIgnoreCase("Réessayer")){
                    //launchActivity(context, MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_NEW_TASK, null, null);
                }
                if ( negativeMessage.equalsIgnoreCase("Réessayer") && DeviceManagerUtils.isConnected(context) ){
                    dialog.dismiss();
                }
            }
        });
        alertDialog.setPositiveButton(positiveMessage, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.showActionInToast(context, positiveMessage);
                if (activity != null)
                    activity.onBackPressed();
                if (negativeMessage.equalsIgnoreCase("Quitter")){
                    activity.finish();
                }

            }
        });

        alertDialog.setCancelable(false);

        // Showing Alert Message
        alertDialog.show();
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
        int snackBarTextId = com.google.android.material.R.id.snackbar_text;
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
}
