package com.example.weatherapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(

    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var latitude: Double,
    var longitude: Double

)
