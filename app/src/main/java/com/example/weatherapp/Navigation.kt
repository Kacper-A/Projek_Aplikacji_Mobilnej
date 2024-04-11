package com.example.weatherapp

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.weatherapp.data.ItemViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.data.ItemViewModelFactory
import com.example.weatherapp.data.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.weatherapp.data.WeatherData
import kotlinx.coroutines.Job

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Navigation() {













    val viewModel: ItemViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "ItemViewModel",
        ItemViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    viewModel.fetchItems()



    var itemsState = viewModel.itemsState.collectAsState(initial = emptyList())


    var longitude = 0.0
    var latitude = 0.0

    var didFoundSelectedPlace = false
    itemsState.value.forEach { item ->
        if(item.id == 1)
        {
            longitude = item.longitude
            latitude = item.latitude
            didFoundSelectedPlace = true
        }
    }

    if(didFoundSelectedPlace)
    {

    }
    else
    {
        longitude = 0.0
        latitude = 0.0
        viewModel.addItemWithId("selectedPlace",0.0,0.0,1)
    }

    var data by remember { mutableStateOf<WeatherData?>(null) }

    var loading by remember { mutableStateOf(true) }





    GlobalScope.launch(Dispatchers.Main) {
        data = RetrofitInstance.api.getData(latitude,longitude)
        loading = false
    }












    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            if (loading == true)
            {
                LoadingScreen()
            }
            else
            {
                MainScreen(viewModel,navController)
            }
        }
        composable("Settings")
        {
            Settings(viewModel,navController)
        }
        composable("LocationList")
        {
            LocationList(viewModel,navController)
        }
        composable("NewLocation")
        {
            NewLocation(viewModel,navController)
        }

        composable("refreshDataButtonClickedFromMainScreenStart")
        {
            //after refresh change to MainScreen

            if(!loading) {
                println("test")
                loading = true
                GlobalScope.launch(Dispatchers.Main) {
                    data = RetrofitInstance.api.getData(latitude, longitude)
                    loading = false

                    println("data print: ${data}")
                }
                navController.navigate("MainScreen")
            }







        }


    }
}