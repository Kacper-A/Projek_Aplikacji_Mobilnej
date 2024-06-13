package com.example.weatherapp

import android.health.connect.datatypes.units.Temperature
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherapp.data.ItemViewModel
import com.example.weatherapp.data.WeatherData

import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.core.cartesian.Scroll
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer

import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Shape
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter




@Composable
fun MainScreen(viewModel: ItemViewModel, navController: NavHostController, data: WeatherData?, cartesianChartModelProducer: CartesianChartModelProducer)
{
    println(data)

    //println(data?.hourly?.temperature_2m)
    //println(data?.hourly?.time)




    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        //Text("mainScreen",fontSize = 20.sp)


        var selectedType by remember { mutableStateOf("Temperature") } //standardowo będzie Temperature, zmienna żeby kolorować na zielono zaznaczony przycisk
        var scrollable = true
        var initialScroll = Scroll.Absolute.Start


        val timeStrings: List<String>? = data?.hourly?.time
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val timeList = timeStrings?.map { LocalDateTime.parse(it, formatter) }
        //println(timeStrings?.map { LocalDateTime.parse(it, formatter) })
        val temperatureList: List<Double>? = data?.hourly?.temperature_2m//?.take(24)
        val windSpeedList: List<Double>? = data?.hourly?.windspeed_10m//?.take(24)
        val relativeHumidityList: List<Int>? = data?.hourly?.relativehumidity_2m
        val precipitationList: List<Double>? = data?.hourly?.precipitation
        var surfacepressureList: List<Double>? = data?.hourly?.surface_pressure

        //println("precipitationList values: "+precipitationList)
        surfacepressureList = surfacepressureList?.map{it-1000}

        //println(windSpeedList) //czemu to kurwa jest null????????

        val dispDataTemperature = if (timeList != null && temperatureList != null) {
            timeList.zip(temperatureList).toMap()
        } else {
            emptyMap()
        }


        val dispDataWindSpeed = if (timeList != null && windSpeedList != null) {
            timeList.zip(windSpeedList).toMap()
        } else {
            emptyMap()
        }

        val disprelativeHumidity = if (timeList != null && relativeHumidityList != null) {
            timeList.zip(relativeHumidityList).toMap()
        } else {
            emptyMap()
        }

        val dispPrecipitation= if (timeList != null && precipitationList != null) {
            timeList.zip(precipitationList).toMap()
        } else {
            emptyMap()
        }

        val dispSurfacepressure= if (timeList != null && surfacepressureList != null) {
            timeList.zip(surfacepressureList).toMap()
        } else {
            emptyMap()
        }

        //val xToDateMapKeyTemperature = ExtraStore.Key<Map<Long, LocalDateTime>>()
        //val xToDateMapKeyWindSpeed = ExtraStore.Key<Map<Long, LocalDateTime>>()
        val xToDatesTemperature = dispDataTemperature.keys.associateBy { it.toEpochSecond(ZoneOffset.UTC) }
        //val xToDatesWindSpeed = dispDataWindSpeed.keys.associateBy { it.toEpochSecond(ZoneOffset.UTC)  }










        cartesianChartModelProducer.tryRunTransaction {
            //println( series(xToDatesTemperature.keys, dispDataTemperature.values) )
            //println("test")
            lineSeries { series(dispDataTemperature.values) }
            //lineSeries { series(x = xToDatesTemperature.keys,y = ) }
            //updateExtras { it[xToDateMapKeyTemperature] = xToDatesTemperature }
        }

        //                       [keys , values]
        //dispDataTemperature to [2024-05-13T00:00,10.6]
        //xToDatesTemperature to [1715558400,2024-05-13T00:00]

        //println(dispDataTemperature.values)


        var todaysStart = xToDatesTemperature.keys.firstOrNull() ?: 0
       // println(xToDatesTemperature.keys.firstOrNull())
        //println(xToDatesTemperature.keys)



        val daysFormatter = CartesianValueFormatter { x, _, _ ->



            var calculatedTimestamp = (todaysStart+ x.toInt() * 3600);
            val date = LocalDateTime.ofInstant(Instant.ofEpochSecond(calculatedTimestamp), ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("EEE HH")
            val formattedDate = date.format(formatter)
            //println(calculatedTimestamp)
            //println(formattedDate)

            formattedDate

            //println("test:"+x.toBigDecimal().toPlainString());
            //SimpleDateFormat("EEE HH").format(todaysStart+ x.toInt() * 3600)

            //x.toBigDecimal().toPlainString()

        }


        Spacer(modifier = Modifier.height(8.dp))

        CartesianChartHost(
            chart =
            rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis = rememberStartAxis(),
                bottomAxis =
                rememberBottomAxis(
                    guideline = null,
                    labelRotationDegrees = -90.0f,


                    itemPlacer = remember { AxisItemPlacer.Horizontal.default(spacing = 1, addExtremeLabelPadding = false) },
                    valueFormatter = daysFormatter,

                ),
            ),

            marker = rememberDefaultCartesianMarker(TextComponent.build{this.background = rememberShapeComponent(
                Shape.Pill, Color(0xFF41FF00) //0 dokumentacji, 1 przykład, nw jak to działa ale jest i pięknie pokazuje że można tego użyć
            )}),
            modelProducer = cartesianChartModelProducer,
            scrollState = rememberVicoScrollState(scrollable, initialScroll),

            )


        LaunchedEffect(selectedType) {
            when (selectedType) {
                "Temperature" -> {
                    cartesianChartModelProducer.tryRunTransaction {
                        lineSeries { series(dispDataTemperature.values) }
                    }
                }
                "Relative humidity" -> {
                    println(disprelativeHumidity.values)
                    cartesianChartModelProducer.tryRunTransaction {
                        lineSeries { series(disprelativeHumidity.values) }
                    }
                }
                "Wind speed" -> {
                    cartesianChartModelProducer.tryRunTransaction {
                        lineSeries { series(dispDataWindSpeed.values) }
                    }
                }
                "Precipitation" ->{
                    cartesianChartModelProducer.tryRunTransaction {
                        lineSeries { series(dispPrecipitation.values) }
                    }
                }
                "Surfacepressure" ->{
                    cartesianChartModelProducer.tryRunTransaction {
                        lineSeries { series(dispSurfacepressure.values) }
                    }
                }
            }
        }






        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier
            .height(30.dp)
            .width(75.dp)
            .background(Color.Gray)
            .clickable {
                println("settings button pressed")
                navController.navigate("Settings")
            })
        {Text(text = "Settings", modifier = Modifier.fillMaxWidth(), // Fill the box width
            textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier
            .height(30.dp)
            .width(75.dp)
            .background(Color.Gray)
            .clickable {
                println("request send again")

                navController.navigate("refreshDataButtonClickedFromMainScreenStart")


            })
        {Text(text = "Refresh data from api", modifier = Modifier.fillMaxWidth(), // Fill the box width
            textAlign = TextAlign.Center)
        }

            Spacer(modifier = Modifier.height(40.dp))

            Box(modifier = Modifier
                .height(50.dp)
                .width(200.dp)
                .background(if(selectedType!="Temperature"){Color.Gray} else {Color.Green})
                .clickable {

                    /*
                    cartesianChartModelProducer.tryRunTransaction {
                        lineSeries { series(dispDataTemperature.values) }
                    }*/
                    selectedType = "Temperature"

                })
            {Text(text = "Temperature", modifier = Modifier.fillMaxWidth(), // Fill the box width
                textAlign = TextAlign.Center)
            }
            
            Spacer(modifier = Modifier.height(40.dp))


            Box(modifier = Modifier
                .height(50.dp)
                .width(200.dp)
                .background(if(selectedType!="Relative humidity"){Color.Gray} else {Color.Green})
                .clickable {
                    selectedType = "Relative humidity"
                })
            {Text(text = "Relative humidity", modifier = Modifier.fillMaxWidth(), // Fill the box width
                textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Box(modifier = Modifier
                .height(50.dp)
                .width(200.dp)
                .background((if(selectedType!="Wind speed"){Color.Gray} else {Color.Green}))
                .clickable {


                    selectedType = "Wind speed"


                })
            {Text(text = "Wind speed", modifier = Modifier.fillMaxWidth(), // Fill the box width
                textAlign = TextAlign.Center)
            }


            Spacer(modifier = Modifier.height(40.dp))

            Box(modifier = Modifier
                .height(50.dp)
                .width(200.dp)
                .background((if(selectedType!="Precipitation"){Color.Gray} else {Color.Green}))
                .clickable {


                    selectedType = "Precipitation"


                })
            {Text(text = "Precipitation", modifier = Modifier.fillMaxWidth(), // Fill the box width
                textAlign = TextAlign.Center)
            }

        Spacer(modifier = Modifier.height(40.dp))

        Box(modifier = Modifier
            .height(50.dp)
            .width(200.dp)
            .background((if(selectedType!="Surfacepressure"){Color.Gray} else {Color.Green}))
            .clickable {


                selectedType = "Surfacepressure"


            })
        {Text(text = "Surface pressure -1000", modifier = Modifier.fillMaxWidth(), // Fill the box width
            textAlign = TextAlign.Center)
        }












    }

}




