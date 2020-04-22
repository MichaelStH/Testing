package com.riders.testing.activities;

import android.Manifest;
import android.content.Context;
import android.content.res.AssetManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.riders.testing.R;
import com.riders.testing.adapters.WeatherCityAdapter;
import com.riders.testing.application.MyApplication;
import com.riders.testing.bus.LocationFetchedEvent;
import com.riders.testing.constants.Const;
import com.riders.testing.model.weather.CityListModel;
import com.riders.testing.model.weather.WeatherResponse;
import com.riders.testing.services.GPSTracker;
import com.riders.testing.utils.DeviceManagerUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity
        implements TextView.OnEditorActionListener, AdapterView.OnItemClickListener,
        LocationListener {

    // Tag & Context
    private static final String TAG = WeatherActivity.class.getSimpleName();
    private Context context;
    MyApplication instance;

    // Views
    @BindView(R.id.autocompleteTV_city)
    AppCompatAutoCompleteTextView autoCompleteCityName;
    /*@BindView(R.id.sp_city_list)
    AppCompatSpinner spinner;*/
    @BindView(R.id.iv_weather_icon)
    ImageView ivWeatherIcon;
    @BindView(R.id.tv_weather_city_name)
    TextView tvWeatherCityName;
    @BindView(R.id.tv_weather_city_country)
    TextView tvWeatherCityCountry;
    @BindView(R.id.tv_weather_main_description)
    TextView tvWeatherDescription;
    @BindView(R.id.tv_weather_city_temperature)
    TextView tvWeatherCityTemperature;
    @BindView(R.id.tv_weather_extra_cloudiness)
    TextView tvWeatherExtraCloudiness;
    @BindView(R.id.tv_weather_extra_humidity)
    TextView tvWeatherExtraHumidity;
    @BindView(R.id.tv_weather_extra_pressure)
    TextView tvWeatherExtraPressure;
    @BindView(R.id.tv_weather_extra_wind)
    TextView tvWeatherExtraWind;
    @BindView(R.id.btn_current_location)
    Button currentLocationButton;

    /* RxJava / RxAndroid */

    // Disposable is used to dispose the subscription when an Observer
    // no longer wants to listen to Observable
    // To prevent memory leak, for example when a task is done
    // and the activity/fragment is already destroyed,
    // the observer try to update the activity/fragment's UI that has been destroyed

    // CompositeDisposable can maintain list of subscriptions in  pool
    // and can dispose them all at once
    // Disposing them in Destroy one bye one is a tedious task and it can be error
    // prone as you might forgot to dispose.
    // In this case we can use CompositeDisposable.
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Observable<List<CityListModel>> mCityListObservable;

    private ArrayList<CityListModel> testList;

    private WeatherCityAdapter mSpinnerAdapter;
    private Reader mReader;
    private Gson mGson;
    private AssetManager mAssetManager;


    /////////////////////////////////////
    //
    // OVERRIDE
    //
    /////////////////////////////////////
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // init variables
        instance = MyApplication.getInstance();
        context = this;
        mAssetManager = instance.getAssets();

        ButterKnife.bind(this);

        setListeners();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Log.d(TAG, "device's sdk version is above 6.0+");

            //Verify permission for Android 6.0+
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Log.i(TAG, "You require permissions!.");

                            try {
                                mCityListObservable = getDataFromJson("city.list.json");

                                compositeDisposable.add(
                                        mCityListObservable.subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeWith(getCitiesObserver())
                                );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            Log.e(TAG, "permission denied");
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        }
                    })
                    .check();
        } else {
            Log.d(TAG, "device's sdk version is : " + Build.VERSION.SDK_INT);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // don't send events once the activity is destroyed
        compositeDisposable.clear();
    }
    /////////////////////////////////////
    //
    // OVERRIDE
    //
    /////////////////////////////////////


    /////////////////////////////////////
    //
    // BUTTERKNIFE
    //
    /////////////////////////////////////
    @OnClick(R.id.btn_current_location)
    void onCurrentLocationButtonClicked() {

        new GPSTracker(context, this);
    }

    /////////////////////////////////////
    //
    // BUTTERKNIFE
    //
    /////////////////////////////////////


    /////////////////////////////////////
    //
    // BUS
    //
    /////////////////////////////////////
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationFetchedEventResult(LocationFetchedEvent event) {
        Log.e(TAG, "onLocationFetchedEvent()");

        Location location = event.getLocation();

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Log.e(TAG, latitude + ", " + longitude);

        DeviceManagerUtils
                .getDeviceLocationWithRX(location, context)
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(@NonNull String s) {
                        Log.e(TAG, "final string city returned : " + s);

                        getCurrentWeather(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                    }
                });
    }
    /////////////////////////////////////
    //
    // BUS
    //
    /////////////////////////////////////


    /////////////////////////////////////
    //
    // CLASS METHODS
    //
    /////////////////////////////////////
    private void setListeners() {
//        spinner.setOnItemSelectedListener(this);
        autoCompleteCityName.setOnItemClickListener(this);
        autoCompleteCityName.setOnEditorActionListener(this);
    }

    private void setViews(ArrayList<CityListModel> cityList) {

//        mSpinnerAdapter = new WeatherCityAdapter(context, cityList, this);
//        autoCompleteCityName.setAdapter(mSpinnerAdapter);
//        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(mSpinnerAdapter);

        mSpinnerAdapter = new WeatherCityAdapter(
                context,
                R.layout.row_city_spinner,
                cityList);
        // Set the minimum number of characters, to show suggestions
        autoCompleteCityName.setThreshold(1);
        autoCompleteCityName.setAdapter(mSpinnerAdapter);
    }

    public void displayData(WeatherResponse weatherResponse) {
        // Load weather icon
        Glide.with(this)
                .load(getWeatherIconFromApi(weatherResponse.getWeather().get(0).getIcon()))
                .into(ivWeatherIcon);

        // Load city name
        String cityName = weatherResponse.getName() +
                instance.getResources().getString(R.string.separator_placeholder);
        tvWeatherCityName.setText(cityName);
        tvWeatherCityCountry.setText(weatherResponse.getSys().getCountry());
        tvWeatherDescription.setText(weatherResponse.getWeather().get(0).getDescription());
        String temperature = (int) Math.round(weatherResponse.getMain().getTemperature()) +
                instance.getResources().getString(R.string.degree_placeholder);
        tvWeatherCityTemperature.setText(temperature);

        long sunriseMillis = weatherResponse.getSys().getSunrise();
        long sunsetMillis = weatherResponse.getSys().getSunset();

        Log.d(TAG, "sunrise time : " + formatMillisToTimeHoursMinutesSeconds(sunriseMillis));

        Log.d(TAG, "cloudiness : " + weatherResponse.getClouds().getCloudiness());

        String cloudiness = weatherResponse.getClouds().getCloudiness() + " " +
                instance.getResources().getString(R.string.percent_placeholder);
        tvWeatherExtraCloudiness.setText(cloudiness);

        String humidity = weatherResponse.getMain().getHumidity() + " " +
                instance.getResources().getString(R.string.percent_placeholder);
        tvWeatherExtraHumidity.setText(humidity);

        String pressure = weatherResponse.getMain().getPressure() + " " +
                instance.getResources().getString(R.string.pressure_unit_placeholder);
        tvWeatherExtraPressure.setText(pressure);

        String wind = weatherResponse.getMain().getHumidity() + " " +
                instance.getResources().getString(R.string.kilometer_unit_placeholder);
        tvWeatherExtraWind.setText(wind);
    }

    public String getWeatherIconFromApi(String weatherIconId) {
        return Const.BASE_ENDPOINT_WEATHER_ICON + weatherIconId + Const.WEATHER_ICON_SUFFIX;
    }

    public String formatMillisToTimeHoursMinutesSeconds(long millis) {

        Date date = new Date(millis);
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }
    /////////////////////////////////////
    //
    // CLASS METHODS
    //
    /////////////////////////////////////


    /////////////////////////////////////
    //
    // LISTENERS
    //
    /////////////////////////////////////
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        View v = getCurrentFocus();

        if (v != null) {
            // Dismiss keyboard
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        CityListModel city = (CityListModel) parent.getItemAtPosition(position);

        String szCity = city.getName() + "," + city.getCountry();
        Log.d(TAG, "position selected, with value of " + city);

        getCurrentWeather(szCity.toLowerCase());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                || (actionId == EditorInfo.IME_ACTION_DONE)) {
            Log.e(TAG, "Done pressed");
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
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
    /////////////////////////////////////
    //
    // LISTENERS
    //
    /////////////////////////////////////


    /////////////////////////////////////
    //
    // RETROFIT
    //
    /////////////////////////////////////
    public void getCurrentWeather(String city) {

        Call<WeatherResponse> call = MyApplication.getInstance().getWeatherApiRestClient()
                .getApiService().getCurrentWeatherByCityName(city);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                if (200 != response.code()) {
                    Log.e(TAG, "error code : " + response.code());
                } else {
                    if (response.isSuccessful()) {

                        if (null == response.body()) {
                            Log.e(TAG,
                                    "body is null, check code in log ---- retrieve code : " +
                                            response.code());

                        } else {
                            WeatherResponse weatherResponse = response.body();
                            displayData(weatherResponse);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }
    /////////////////////////////////////
    //
    // RETROFIT
    //
    /////////////////////////////////////


    /////////////////////////////////////
    //
    // RX
    //
    /////////////////////////////////////
    public Observable<List<CityListModel>> getDataFromJson(String fileName) {

        return Observable.fromCallable(new Callable<List<CityListModel>>() {
            @Override
            public List<CityListModel> call() throws Exception {

                mGson = new Gson();
                String json = null;

                InputStream is = mAssetManager.open(fileName);

                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                json = new String(buffer, "UTF-8");

                mGson.toJson(json);

                return mGson.fromJson(json, new TypeToken<List<CityListModel>>() {
                }.getType());
            }
        });
    }

    public DisposableObserver<List<CityListModel>> getCitiesObserver() {

        // TODO : test list
        testList = new ArrayList<>();

        return new DisposableObserver<List<CityListModel>>() {
            @Override
            public void onNext(List<CityListModel> cityListModels) {

                for (CityListModel element : cityListModels) {

                    if (element.getCountry().equals(Const.WEATHER_COUNTRY_CODE_FRANCE)
                            || element.getCountry().equals(Const.WEATHER_COUNTRY_CODE_GUADELOUPE)
                            || element.getCountry().equals(Const.WEATHER_COUNTRY_CODE_MARTINIQUE)
                            || element.getCountry().equals(Const.WEATHER_COUNTRY_CODE_GUYANE)
                            || element.getCountry().equals(Const.WEATHER_COUNTRY_CODE_REUNION))
                        testList.add(element);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "error : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                setViews(testList);
            }
        };
    }
    /////////////////////////////////////
    //
    // RX
    //
    /////////////////////////////////////


}
