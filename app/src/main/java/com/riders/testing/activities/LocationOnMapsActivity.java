package com.riders.testing.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.riders.testing.R;
import com.riders.testing.application.MyApplication;
import com.riders.testing.model.GooglePlacesResponse;
import com.riders.testing.model.PlacesList;
import com.riders.testing.services.GPSTracker;
import com.riders.testing.utils.DeviceManagerUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cpu on 10/12/2015.
 */
public class LocationOnMapsActivity extends FragmentActivity implements LocationListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LocationOnMapsActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;

    private GoogleMap mGoogleMap;
    private SupportMapFragment mSupportMapFragment;
    private Location mLocation;
    private LocationManager mLocationManager;
    private Criteria mCriteria;
    private String mProvider = "";

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Google Places
    //GooglePlaces googlePlaces;

    // Places List
    PlacesList nearPlaces;

    // GPS Location
    GPSTracker gps;

    // Google API Key
    private static final String API_KEY = MyApplication.getAppContext().getResources().getString( R.string.google_maps_key );

    // Google Places serach url's
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_TEXT_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";

    private double _latitude;
    private double _longitude;
    private double _radius;

    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();

    // KEY Strings
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_NAME = "name"; // name of the place
    public static String KEY_VICINITY = "vicinity"; // Place area name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        //Set the layout then
        setContentView(R.layout.activity_location_on_maps);

        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_location_on_maps_googleMap);
        mSupportMapFragment.getMapAsync(this);


        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        Call<GooglePlacesResponse> call = MyApplication.getGooglePlacesApiRestClient().getApiService().getPlaces(100, API_KEY);
        call.enqueue(new Callback<GooglePlacesResponse>() {
            @Override
            public void onResponse(Call<GooglePlacesResponse> call, Response<GooglePlacesResponse> response) {
                Log.e( "OHOH Retrofit", "OK ! - " + response.body().getStatus() );
            }

            @Override
            public void onFailure(Call<GooglePlacesResponse> call, Throwable t) {

            }
        });

    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(DeviceManagerUtils.getDeviceLocationToString(location, this)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
        locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);

        DeviceManagerUtils.getDeviceLocation(location, this);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        setMapUISettings( mGoogleMap );
        setLocationSettings();

        //setGooglePlacesAPI();

        //String url = PLACES_SEARCH_URL + "&key=" + API_KEY;
        //Log.e("OHOH", "test url: " + url);
        //new LoadPlaces().execute(url);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setMapUISettings(GoogleMap googleMap){
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    public void setLocationSettings(){
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mCriteria = new Criteria();

        mProvider = mLocationManager.getBestProvider(mCriteria, true);
        try {

            mLocation = mLocationManager.getLastKnownLocation(mProvider);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        if (mLocation != null) {
            onLocationChanged(mLocation);
        }

        try {

            mLocationManager.requestLocationUpdates(mProvider, 20000, 0, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void setGooglePlacesAPI(){
        // creating GPS Class object
        gps = new GPSTracker(this);

        // check if GPS location can get
        if (gps.canGetLocation()) {
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {
            // Can't get user's current location
            //alert.showAlertDialog(MainActivity.this, "GPS Status",
            //        "Couldn't get location information. Please enable GPS",
            //        false);
            // stop executing code by return
            return;
        }

        // calling background Async task to load Google Places
        // After getting places from Google all the data is shown in listview
        new LoadPlaces().execute();

    }


    /** A method to download json data from url */
    @SuppressLint("LongLogTag")
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }

    /**
     * Background Async Task to Load Google places
     * */
    class LoadPlaces extends AsyncTask<String, String, String> {

        String data = null;

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            */
        }

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * and show the data in UI
         * Always use runOnUiThread(new Runnable()) to update UI from background
         * thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {
            Log.e("OHOH", "result : " + file_url);

            /*
            // dismiss the dialog after getting all products
            //pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Get json response status
                    String status = nearPlaces.status;

                    // Check for all possible status
                    if(status.equals("OK")){
                        // Successfully got places details
                        if (nearPlaces.results != null) {
                            // loop through each place
                            for (Place place : nearPlaces.results) {
                                HashMap<String, String> map = new HashMap<String, String>();

                                // Place reference won't display in listview - it will be hidden
                                // Place reference is used to get "place full details"
                                map.put(KEY_REFERENCE, place.reference);

                                // Place name
                                map.put(KEY_NAME, place.name);

                                Log.e("OHOH", "boucle asynctask : " + place.name);


                                // adding HashMap to ArrayList
                                placesListItems.add(map);
                            }
                            // list adapter
                            ListAdapter adapter = new SimpleAdapter(MainActivity.this, placesListItems,
                                    R.layout.list_item,
                                    new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
                                    R.id.reference, R.id.name });

                            // Adding data into listview
                            lv.setAdapter(adapter);
                        }
                    }
                    else if(status.equals("ZERO_RESULTS")){
                        // Zero results found
                        Utils.showActionInToast(LocationOnMaps.this, "Near Places " + "Sorry no places found. Try to change the types of places");
                    }
                    else if(status.equals("UNKNOWN_ERROR"))
                    {
                        Utils.showActionInToast(LocationOnMaps.this, "Places Error " + "Sorry unknown error occured.");
                    }
                    else if(status.equals("OVER_QUERY_LIMIT"))
                    {
                        Utils.showActionInToast(LocationOnMaps.this, "Places Error " + "Sorry query limit to google places is reached");
                    }
                    else if(status.equals("REQUEST_DENIED"))
                    {
                        Utils.showActionInToast(LocationOnMaps.this, "Places Error " + "Sorry error occured. Request is denied");
                    }
                    else if(status.equals("INVALID_REQUEST"))
                    {
                        Utils.showActionInToast(LocationOnMaps.this, "Places Error " + "Sorry error occured. Invalid Request");
                    }
                    else
                    {
                        Utils.showActionInToast(LocationOnMaps.this, "Places Error " + "Sorry error occured.");
                    }
                }
            });
            */
        }

    }
}
