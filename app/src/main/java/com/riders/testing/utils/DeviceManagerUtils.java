package com.riders.testing.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.riders.testing.R;
import com.riders.testing.application.MyApplication;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Michaël on 07/03/2017.
 */

public class DeviceManagerUtils {


    private static final String TAG = DeviceManagerUtils.class.getSimpleName();

    /** This class can't be instantiated. */
    private DeviceManagerUtils() {
    }


    //for example, permission can be "android.permission.WRITE_EXTERNAL_STORAGE"
    public static boolean hasPermission(Context context, String permission) {
        String prefix = "android.permission.";

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    if (p.equals(prefix + permission.toUpperCase())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean hasMarshmallowPermission(Context context, String permission) {
        return false;
    }


    /**
     * Return the IP of the device
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getDeviceIpAddress(Context context) {

        String ip = null;

        try {
            if (hasPermission(context, "access_wifi_state")) {
                WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            } else {
                Log.e(TAG, "android.permission.access_wifi_state is missing, did you add this one in the manifest ?");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ip;
    }

    public static long getAvailableBlocks() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getBlockCount();
        long megAvailable = bytesAvailable / 1048576;

        return megAvailable;
    }

    /**
     * http://stackoverflow.com/questions/3394765/how-to-check-available-space-on-android-device-on-mini-sd-card
     * @return Number of bytes available on External storage
     */
    public static long getAvailableSpaceInBytes() {
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();

        return availableSpace;
    }


    /**
     * http://stackoverflow.com/questions/3394765/how-to-check-available-space-on-android-device-on-mini-sd-card
     * @return Number of kilo bytes available on External storage
     */
    public static long getAvailableSpaceInKB() {
        final long SIZE_KB = 1024L;
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace / SIZE_KB;
    }

    /**
     * http://stackoverflow.com/questions/3394765/how-to-check-available-space-on-android-device-on-mini-sd-card
     * @return Number of Mega bytes available on External storage
     */
    public static long getAvailableSpaceInMB() {
        final long SIZE_KB = 1024L;
        final long SIZE_MB = SIZE_KB * SIZE_KB;
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace / SIZE_MB;
    }

    /**
     * http://stackoverflow.com/questions/3394765/how-to-check-available-space-on-android-device-on-mini-sd-card
     * @return Number of gega bytes available on External storage
     */
    public static long getAvailableSpaceInGB() {
        final long SIZE_KB = 1024L;
        final long SIZE_GB = SIZE_KB * SIZE_KB * SIZE_KB;
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace / SIZE_GB;
    }


    public static void getDeviceLocation(Location location, Context context) {

        Log.i(TAG, "--- Class Utils ---  getDeviceLocation() ");

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        //get the address
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        StringBuilder builderAddr = new StringBuilder();
        StringBuilder builderCity = new StringBuilder();

        try {
            List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i = 0; i < maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                String cityStr = address.get(0).getLocality();

                Log.e("OHOH", "Adresse : " + addressStr + " | " + "City : " + cityStr); //This will display the final address.

                builderAddr.append(addressStr);
                builderAddr.append(" ");

                builderCity.append(cityStr);
                builderCity.append(" ");
            }

            String finalAddress = builderAddr.toString(); //This is the complete address.
            String finalCity = builderCity.toString(); //This is the complete address.

            Log.e("OHOH", "Adresse : " + finalAddress + " | " + "City : " + finalCity); //This will display the final address.

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();

        }
    }

    public static String getDeviceLocationToString(Location location, Context context) {

        Log.i(TAG, "--- Class Utils ---  getDeviceLocation() ");

        String finalAddress = ""; //This is the complete address.
        String finalCity = ""; //This is the complete address.

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        //get the address
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        StringBuilder builderAddr = new StringBuilder();
        StringBuilder builderCity = new StringBuilder();

        try {
            List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i = 0; i < maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                String cityStr = address.get(0).getLocality();
                builderAddr.append(addressStr);
                builderAddr.append(" ");

                builderCity.append(cityStr);
                builderCity.append(" ");
            }

            finalAddress = builderAddr.toString(); //This is the complete address.
            finalCity = builderCity.toString(); //This is the complete address.

            Log.e("OHOH", "Adresse : " + finalAddress + " | " + "City : " + finalCity); //This will display the final address.

        } catch (IOException |NullPointerException e) {
            e.printStackTrace();
        }

        return finalAddress;
    }


    @SuppressLint("MissingPermission")
    public static String getDeviceIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) MyApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        // Call Dexter to easily check permission
        Dexter.withActivity((Activity) context)
                .withPermission(Manifest.permission.READ_PHONE_STATE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
        return telephonyManager.getDeviceId();
    }

    /**
     * Check the Internet connection
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static int getRandomImageFromArrayXML( Context context ){
        final TypedArray imgs = context.getResources().obtainTypedArray(R.array.random_images_array);
        final Random rand = new Random();
        final int rndInt = rand.nextInt(imgs.length());
        final int resID = imgs.getResourceId(rndInt, 0);
        return resID;
    }

    public static int getRandomImageFromDrawable(){
        final Class drawableClass = R.drawable.class;
        final Field[] fields = drawableClass.getFields();
        int resID = 0;

        final Random rand = new Random();
        int rndInt = rand.nextInt(fields.length);
        try {
            resID = fields[rndInt].getInt(drawableClass);
            //img.setImageResource(resID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resID;
    }
}
