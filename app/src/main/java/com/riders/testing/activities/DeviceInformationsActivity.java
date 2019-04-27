package com.riders.testing.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.riders.testing.R;
import com.riders.testing.utils.DeviceManagerUtils;

import java.lang.reflect.Method;


/**
 * Created by cpu on 05/01/2016.
 */
public class DeviceInformationsActivity extends AppCompatActivity {

    private static final String TAG = DeviceInformationsActivity.class.getSimpleName();

    private TextView tvDevice = null,
            tvModel = null,
            tvBrand = null,
            tvScreenHeight = null,
            tvScreenWidth = null,
            tvHardware = null,
            tvProduct = null,
            tvManufacturer = null,
            tvSerial = null,
            tvBoard = null,
            tvBootloader = null,
            tvDisplay = null,
            tvFingerprint = null,
            tvID = null,
            tvTags = null,
            tvType = null;


    private String deviceDevice = null,
            deviceModel = null,
            deviceBrand = null,
            deviceHardware = null,
            deviceProduct = null,
            deviceManufact = null,
            deviceSerial = null,
            deviceIMEI = null,
            deviceBoard = null,
            deviceBootlaoder = null,
            deviceDisplay = null,
            deviceFingerprint = null,
            deviceID = null,
            deviceTags = null,
            deviceType = null;

    private int deviceScreenHeight = 0,
            deviceScreenWidth = 0,
            deviceVersionSDK = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_informations);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(TAG);

        //Initialize views
        initViews();


        //Retrieve datas
        getInfos();

        //SetText
        setTexts();

    }


    /**
     * Initialize Views
     */
    public void initViews(){

        Log.i(TAG, "Init Views");

        tvDevice = (TextView) findViewById(R.id.tv_device_device);
        tvModel = (TextView)findViewById(R.id.tv_device_model);
        tvBrand = (TextView)findViewById(R.id.tv_device_brand);
        tvScreenHeight = (TextView)findViewById(R.id.tv_device_screen_height);
        tvScreenWidth = (TextView)findViewById(R.id.tv_device_screen_width);
        tvHardware = (TextView)findViewById(R.id.tv_device_hardware);
        tvProduct = (TextView)findViewById(R.id.tv_device_product);
        tvManufacturer = (TextView) findViewById(R.id.tv_device_manufacturer);
        tvSerial = (TextView) findViewById(R.id.tv_device_serial);
        tvBoard = (TextView)findViewById(R.id.tv_device_board);
        tvBootloader = (TextView)findViewById(R.id.tv_device_bootlaoder);
        tvDisplay = (TextView)findViewById(R.id.tv_device_display);
        tvFingerprint = (TextView)findViewById(R.id.tv_device_fingerprint);
        tvID = (TextView)findViewById(R.id.tv_device_id);
        tvTags = (TextView)findViewById(R.id.tv_device_tags);
        tvType = (TextView)findViewById(R.id.tv_device_type);

    }


    /**
     * Retrieve infos from the device
     */
    @SuppressLint("MissingPermission")
    public void getInfos(){

        Log.i(TAG, "Get Informations");

        deviceDevice = Build.DEVICE;
        deviceModel = Build.MODEL;
        deviceBrand = Build.BRAND;

        //Retrieve Screen's height and width
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        deviceScreenHeight= metrics.heightPixels;
        deviceScreenWidth = metrics.widthPixels;

        deviceHardware = Build.HARDWARE;
        deviceProduct = Build.PRODUCT;
        deviceManufact = Build.MANUFACTURER;

        /**
         * http://stackoverflow.com/questions/14161282/serial-number-from-samsung-device-running-android
         *
         * The OP asked about Galaxy Tab 2 and for that indeed the answer was ril.serialnumber (even for the non-3G model - see this gist).
         * According to Himanshu's answer Galaxy Tab 3 uses sys.serialnumber (also backed by this answer).
         * sys.serialnumber makes better sense for tablets as ril.* stands for Radio Interface Layer, something most tablets are not equipped with
         * (ril.serialnumber, respectively, makes better sense for phones).
         *
         * There is no standard API for getting the device serial number
         * (ie the serial number on the packaging - not to be confused with Settings.Secure.ANDROID_ID or the various other "unique" identifiers scattered throughout the API).
         * This means it is up to the manufacturer to decide where to store the device serial (if at all).
         * On the S3 Mini it's ril.serialnumber,
         * on NexusOne it's ro.serialno (gist),
         * on Galaxy Tab 2 it's ril.serialnumber,
         * on Galaxy Tab 3/4 it's ril.serialnumber,
         * on Lenovo Tab it's none of the above.
         *
         * These settings appear to be the usual suspects, when looking for the device serial, but shouldn't be taken for granted,
         * and as such, shouldn't be relied on for tracking unique app installations.
         */

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            deviceSerial = (String) get.invoke(c, "ril.serialnumber", "unknown");
        } catch (Exception e) {
            Log.e(TAG, "Some error occured : " + e.getMessage());
        }


        /**
         * http://stackoverflow.com/questions/1972381/how-to-get-the-devices-imei-esn-programmatically-in-android
         */
        final TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);


        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_PHONE_STATE)
                .withListener(new PermissionListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        deviceIMEI = tm.getDeviceId();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Log.e(TAG, "android.permission.read_phone_state : access not granted");

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                });

        if (deviceIMEI == null || deviceIMEI.length() == 0)
            deviceIMEI = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        deviceBoard = Build.BOARD;
        deviceBootlaoder = Build.BOOTLOADER;
        deviceDisplay = Build.DISPLAY;
        deviceFingerprint = Build.FINGERPRINT;
        deviceID = Build.ID;
        deviceTags = Build.TAGS;
        deviceType = Build.TYPE;
        deviceVersionSDK = Build.VERSION.SDK_INT;


        String logDeviceInfos =
                "Model : " + deviceModel + " \n" +
                "Product : " + deviceProduct + "(don't need) \n" +
                "Manufacturer : " + deviceManufact + "(not necessary) \n" +
                "Serial : " + deviceSerial + " \n" +
                "Brand : " + deviceBrand + " \n" +
                "Display : " + deviceDisplay + "(don't need) \n" +
                "Screen Width : " + deviceScreenWidth + " \n" +
                "Screen Height : " + deviceScreenHeight + " \n" +
                "Hardware : " + deviceHardware + " \n" +
                "Version SDK : API " + deviceVersionSDK + " \n" +
                "IMEI : " + deviceIMEI + "\n";

        Log.i(TAG,logDeviceInfos);


    }

    public void setTexts(){

        Log.i(TAG, "Set Texts");

        tvDevice.setText(deviceDevice);
        tvModel.setText(deviceModel);
        tvBrand.setText(deviceBrand);
        tvScreenHeight.setText(String.valueOf(deviceScreenHeight));
        tvScreenWidth.setText(String.valueOf(deviceScreenWidth));
        tvHardware.setText(deviceHardware);
        tvProduct.setText(deviceProduct);
        tvManufacturer.setText(deviceManufact);
        tvSerial.setText(deviceSerial);
        tvBoard.setText(deviceBoard);
        tvBootloader.setText(deviceBootlaoder);
        tvDisplay.setText(deviceDisplay);
        tvFingerprint.setText(deviceFingerprint);
        tvID.setText(deviceID);
        tvTags.setText(deviceTags);
        tvType.setText(deviceType);

    }
}
