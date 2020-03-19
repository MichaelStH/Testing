package com.riders.testing.rest.api;

import com.riders.testing.model.weather.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {

    //Method to retrieve the youtube content

    /**
     * Description: You can call by city name or city name, state and country code.
     * API responds with a list of results that match a searching word.
     *
     * API call:
     * api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}
     * api.openweathermap.org/data/2.5/weather?q={city name},{state}&appid={your api key}
     * api.openweathermap.org/data/2.5/weather?q={city name},{state},{country code}&appid={your api key}
     *
     * Parameters: q city name, state and country code divided by comma, use ISO 3166 country codes.
     * You can specify the parameter not only in English. In this case,
     * the API response should be returned in the same language as the language of requested
     * location name if the location is in our predefined list of more than 200,000 locations.
     *
     * Examples of API calls:
     * api.openweathermap.org/data/2.5/weather?q=London
     * api.openweathermap.org/data/2.5/weather?q=London,uk
     *
     * @param cityName
     * @return
     */
    @GET("/data/2.5/weather?")
    Call<Weather> getCurrentWeatherByCityName(@Query("q") String cityName);

    /**
     * We recommend to call API by city ID to get unambiguous result for your city.
     *
     * List of city ID city.list.json.gz can be downloaded here http://bulk.openweathermap.org/sample/
     *
     * API call:
     * api.openweathermap.org/data/2.5/weather?id={city id}&appid={your api key}
     *
     * Parameters: id City ID
     * Examples of API calls: api.openweathermap.org/data/2.5/weather?id=2172797
     *
     * @param cityID
     * @return
     */
    @GET("/data/2.5/weather?")
    Call<Weather> getCurrentWeatherByCityID(@Query("q") String cityID);

    /**
     * API call: api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={your api key}
     * Parameters: lat, lon coordinates of the location of your interest
     * Examples of API calls: api.openweathermap.org/data/2.5/weather?lat=35&lon=139
     *
     * @param lat
     * @param lon
     * @return
     */
    @GET("/data/2.5/weather?")
    Call<Weather> getCurrentWeatherByGeographicCoordinates(
            @Query("lat") int lat, @Query("lon") int lon);


    /**
     * Description: Please note if country is not specified then the search works for USA as a default.
     * API call:
     * api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}&appid={your api key}
     * Examples of API calls: api.openweathermap.org/data/2.5/weather?zip=94040,us
     *
     * @param zipCodeAndCountryCode
     * @return
     */
    @GET("/data/2.5/weather?")
    Call<Weather> getCurrentWeatherByZipCode(@Query("zip") String zipCodeAndCountryCode);
}
