package com.example.weatherapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.data.ItemViewModel

@Composable
fun Settings(viewModel: ItemViewModel, navController: NavHostController) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.background(Color.Gray)
            .height(100.dp)
            .fillMaxWidth().padding(10.dp)
            .clickable {
                println("LocationList button pressed")
                navController.navigate("LocationList")
            }

        )
        {
            Text(text = "Location List", modifier = Modifier.fillMaxWidth(),textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.background(Color.Gray)
            .height(100.dp)
            .fillMaxWidth().padding(10.dp)


            )
        {
            Text(text = "Settings2", modifier = Modifier.fillMaxWidth(),textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.height(8.dp))


    }

}