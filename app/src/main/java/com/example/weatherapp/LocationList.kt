package com.example.weatherapp

import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherapp.data.Item
import com.example.weatherapp.data.ItemViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun LocationList(viewModel: ItemViewModel, navController: NavHostController) {

    viewModel.fetchItems()
    var itemsState = viewModel.itemsState.collectAsState(initial = emptyList())


    LaunchedEffect(itemsState) {
        // Print the updated itemsState
        println(itemsState)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {

        /*
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier
            .height(30.dp)
            .width(75.dp)
            .background(Color.Gray)
            .clickable {
                println("print data")
                viewModel.fetchItems()



                println(itemsState.value)
            })
        {
            Text(text = "print data", modifier = Modifier.fillMaxWidth(), // Fill the box width
                textAlign = TextAlign.Center)
        }
        */


        Spacer(modifier = Modifier.height(8.dp))
        itemsState.value.forEach { item ->

            if(item.id != 1) //id 1 reserved for api key
            {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(300.dp) // Adjust width as needed
                        .background(Color.Gray)
                        .clickable {
                            println("selected (gray box clicked) item: ${item.name}")


                            //var overwriteItem: Item = Item(1,"selectedPlace",item.latitude,item.longitude)
                            viewModel.updateItem(1,"selectedPlace",item.latitude,item.longitude)
                            viewModel.fetchItems()
                            navController.navigate("refreshDataButtonClickedFromMainScreenStart")


                        }
                ) {
                    Row(){

                        Text(
                            text = item.name,
                            modifier = Modifier.weight(1f), // Expand to fill the available space
                            textAlign = TextAlign.Center
                        )

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.Red)
                                .clickable{
                                    println("delete item: ${item.name}")
                                    viewModel.deleteItem(item)
                                    viewModel.fetchItems()
                                }
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "-",
                                style = TextStyle(fontSize = 20.sp)
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
            }

        }



        Box(modifier = Modifier
            .height(30.dp)
            .width(300.dp)
            .background(Color.Gray)
            .clickable {
                //println("TEST NAVIGATE TO NEW LOCATION")
                navController.navigate("NewLocation")

            }
            .background(Color.Green)
        )
        {
            Text(text = "ADD NEW LOCATION", modifier = Modifier.fillMaxWidth(), // Fill the box width
                textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier
            .height(30.dp)
            .width(300.dp)
            .background(Color.Gray)
            .clickable {
                println("ADD FROM PRESET TEST")
                //todo zaimplementować ten widok z https://github.com/lutangar/cities.json/tree/master do pobierania lokacji
                navController.navigate("CitiesSelectionCountries")
                /*
                val externalStorageDir = Environment.getExternalStorageDirectory()
                val file = File(externalStorageDir, "cities.json")

                try {
                    val fos = FileOutputStream(file)



                    fos.close()
                }
                catch (e: IOException){

                }*/

            }
            .background(Color(0xFF47A0FF))
        )
        {
            Text(text = "ADD FROM PRESET", modifier = Modifier.fillMaxWidth(), // Fill the box width
                textAlign = TextAlign.Center)
        }













    }

}