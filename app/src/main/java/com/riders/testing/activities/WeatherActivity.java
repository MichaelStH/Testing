package com.riders.testing.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.riders.testing.R;
import com.riders.testing.application.MyApplication;
import com.riders.testing.constants.Const;
import com.riders.testing.model.weather.WeatherResponse;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = WeatherActivity.class.getSimpleName();
    private Context context;

    // Views
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

    MyApplication instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        instance = MyApplication.getInstance();
        ButterKnife.bind(this);

        getCurrentWeather();

    }

    public void getCurrentWeather() {

        Call<WeatherResponse> call = MyApplication.getInstance().getWeatherApiRestClient()
                .getApiService().getCurrentWeatherByCityName("Paris");
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
        String cityName = weatherResponse.getName() + instance.getResources().getString(R.string.separator_placeholder);
        tvWeatherCityName.setText(cityName);
        tvWeatherCityCountry.setText(weatherResponse.getSys().getCountry());
        tvWeatherDescription.setText(weatherResponse.getWeather().get(0).getDescription());
        String temperature = (int) Math.round(weatherResponse.getMain().getTemperature()) +
                instance.getResources().getString(R.string.degree_placeholder);
        tvWeatherCityTemperature.setText(temperature);
        ;
    }

    public String getWeatherIconFromApi(String weatherIconId) {
        return Const.BASE_ENDPOINT_WEATHER_ICON + weatherIconId + Const.WEATHER_ICON_SUFFIX;
    }
}
