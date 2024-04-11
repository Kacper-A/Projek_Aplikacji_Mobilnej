package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavHostController
import com.example.weatherapp.data.ItemViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices



@SuppressLint("MissingPermission")
private fun getCurrentLocation(fusedLocationClient: FusedLocationProviderClient, context: Context, callback: (Double, Double) -> Unit) {
    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    callback(latitude, longitude)
                } else {
                    // Handle case where last known location is null
                }
            }
            .addOnFailureListener { e ->
                // Handle failure to retrieve location
            }
    } else {
        // Permission not granted, request it
        //requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewLocation(viewModel: ItemViewModel, navController: NavHostController)
{
    var name by remember { mutableStateOf("Name") }
    var long by remember { mutableStateOf("")}
    var lati by remember { mutableStateOf("")}

    val context = LocalContext.current


    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(LocalContext.current)

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            println("granted")
            // Permission granted, you can perform the location-related operation here
            // For example, you can call a function to get the current location
            // getCurrentLocation()

        } else {
            println("denied")
            // Permission denied, handle accordingly (e.g., show a message to the user)
        }
    }

    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Location name") }

        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = lati ,
            onValueChange = { lati = it },
            label = { Text("latitude") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = long ,
            onValueChange = { long  = it },
            label = { Text("longitude") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier
            .height(30.dp)
            .width(75.dp)
            .background(Color.Gray)
            .clickable {
                println("dummy data created")
                viewModel.addItem(name,lati.toDouble(),long.toDouble())
                viewModel.fetchItems()
                navController.navigate("LocationList")
            })
        {
            Text(text = "Create dummy data", modifier = Modifier.fillMaxWidth(), // Fill the box width
                textAlign = TextAlign.Center)
        }

        Box(modifier = Modifier
            .height(30.dp)
            .width(300.dp)
            .background(Color.Gray)
            .clickable{

                if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {

                    getCurrentLocation(fusedLocationClient, context) { latitude, longitude ->
                        long = longitude.toString()
                        lati = latitude.toString()
                        //println("Latitude: $latitude, Longitude: $longitude")
                        // Update UI or perform other actions with the retrieved location
                    }



                } else {
                    // Permission not granted, request it
                    requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION )
                }

            }

            )

            {

            Text(text = "Get current location", modifier = Modifier.fillMaxWidth(), // Fill the box width
                textAlign = TextAlign.Center)
        }

    }
}