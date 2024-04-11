package com.example.weatherapp.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ItemsRepository (private val itemDao: ItemDao) {

    val allItems = MutableLiveData<List<Item>>()
    val foundItem = MutableLiveData<Item>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addItem(newItem: Item) {
        coroutineScope.launch(Dispatchers.IO) {
            itemDao.insert(newItem)
        }
    }

    fun updateItemDetails(newItem: Item) {
        coroutineScope.launch(Dispatchers.IO) {
            itemDao.update(newItem)
        }
    }

    private fun refreshAllItems() {
        coroutineScope.launch {
            // Get the entire list directly
            val items = itemDao.getAllItems()
            // Assign the list (or null if empty) to allItems.value
            allItems.value = items
        }
    }


    fun deleteItem(item: Item) {
        coroutineScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
            refreshAllItems() // Update allItems after deletion
        }
    }

    fun getItemById(itemId: Int) {
        coroutineScope.launch {
            foundItem.value = itemDao.getItem(itemId) // Use firstOrNull to avoid potential empty Flow emission
        }
    }

    suspend fun getAllItems(): List<Item> {
        return itemDao.getAllItems()
    }







}