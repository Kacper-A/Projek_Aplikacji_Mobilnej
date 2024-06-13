package com.example.weatherapp.data

data class WeatherData (
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Double,
    val current_units: Units,
    val current: Current,
    val hourly_units: Units,
    val hourly: Hourly
)

data class Units(
    val time: String,
    val interval: String,
    val temperature_2m: String,
    val relativehumidity_2m: String,
    val wind_speed_10m: String,
    val precipitation: String,
    val surface_pressure: String
)

data class Current(
    val time: String,
    val interval: Int,
    val temperature_2m: Double,
    val windspeed_10m: Double
)

data class Hourly(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val relativehumidity_2m: List<Int>,
    val windspeed_10m: List<Double>,
    val precipitation: List<Double>,
    val surface_pressure: List<Double>
)