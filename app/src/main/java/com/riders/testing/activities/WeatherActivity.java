package com.riders.testing.activities;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riders.testing.R;
import com.riders.testing.adapters.WeatherCityAdapter;
import com.riders.testing.application.MyApplication;
import com.riders.testing.constants.Const;
import com.riders.testing.model.weather.CityListModel;
import com.riders.testing.model.weather.WeatherResponse;

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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        instance = MyApplication.getInstance();
        context = this;
        mAssetManager = instance.getAssets();

        ButterKnife.bind(this);

        setListeners();

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

    private void setListeners() {
//        spinner.setOnItemSelectedListener(this);
        autoCompleteCityName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityListModel city = (CityListModel) parent.getItemAtPosition(position);

//                fruitDesc.setText(fruit.getDesc());

                String szCity = city.getName() + "," + city.getCountry();
                Log.d(TAG, "position selected, with value of " + city);

                getCurrentWeather(szCity.toLowerCase());
            }
        });
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

    public Observable<List<CityListModel>> getDataFromJson(String fileName) {

        return Observable.fromCallable(new Callable<List<CityListModel>>() {
            @Override
            public List<CityListModel> call() throws Exception {

                mGson = new Gson();
                String json;

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

                    if (element.getCountry().equals("FR"))
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        /*switch (parent.getId()) {
            case R.id.sp_city_list: {

                String city = testList.get(position).getName() +
                        "," +
                        testList.get(position).getCountry().toLowerCase();

                Log.d(TAG, "position selected : " + position + ", with value of " + city);

                getCurrentWeather(city);
            }
            break;

            default:
                break;
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.e(TAG, "nothing selected");
    }

    /*@Override
    public void onCitySelected(CityListModel city) {

        String szCity = city.getName() + "," + city.getCountry();
        Log.d(TAG, "position selected, with value of " + city);

        getCurrentWeather(szCity.toLowerCase());
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();

        // don't send events once the activity is destroyed
        compositeDisposable.clear();
    }

}
