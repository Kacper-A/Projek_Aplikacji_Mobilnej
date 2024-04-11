package com.example.weatherapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface userApi {

    //@GET("v1/forecast?latitude="+lat+"&longitude="+lng+"&hourly=temperature_2m,relativehumidity_2m,precipitation,windspeed_10m,surface_pressure&models=best_match")
    //suspend fun getData(lat:Double,lng:Double) : List<WeatherData>


    @GET("v1/forecast")
    suspend fun getData(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("hourly") hourly: String = "temperature_2m,relativehumidity_2m,precipitation,windspeed_10m,surface_pressure",
        @Query("models") models: String = "best_match"
    ): WeatherData
}