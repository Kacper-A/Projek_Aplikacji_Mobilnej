package com.example.weatherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherapp.data.ItemViewModel
import com.example.weatherapp.data.RetrofitInstance
import com.example.weatherapp.data.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: ItemViewModel, navController: NavHostController)
{




    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text("mainScreen",fontSize = 20.sp)
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
    }

}