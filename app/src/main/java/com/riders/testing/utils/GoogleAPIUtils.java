package com.riders.testing.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by Michaël on 18/11/2017.
 */

public class GoogleAPIUtils {

    /** This class can't be instantiated. */
    private GoogleAPIUtils(){}


    /**
     * Method to verify google play services on the device
     * */
    public static boolean checkPlayServices(Context context, final int PLAY_SERVICES_RESOLUTION_REQUEST) {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable( context );
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) context,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Toast.makeText(context,"This device is not supported.", Toast.LENGTH_LONG).show();
                //context.getApplicationContext().finish();
            }
            return false;
        }
        return true;
    }


}
