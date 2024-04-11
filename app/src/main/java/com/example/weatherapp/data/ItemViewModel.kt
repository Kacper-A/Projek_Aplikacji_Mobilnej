package com.example.weatherapp.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class ItemViewModel(application: Application) : ViewModel() {

    private val repository: ItemsRepository

    var _itemsState = MutableStateFlow<List<Item>>(emptyList())
    val itemsState: StateFlow<List<Item>>
        get() = _itemsState

    private var _selectedItemId = MutableStateFlow<Int?>(null)
    val selectedItemId: StateFlow<Int?>
        get() = _selectedItemId

    init {
        val db = InventoryDatabase.getDatabase(application)
        val dao = db.itemDao()
        repository = ItemsRepository(dao)

        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            val items = repository.getAllItems()
            _itemsState.emit(items)
        }
    }


    fun addItem(name: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val newItem = Item(name = name, latitude = latitude, longitude = longitude, id=0)
            repository.addItem(newItem)
            // Refresh list after addition
            fetchItems()

        }
    }

    fun addItemWithId(name: String, latitude: Double, longitude: Double, id: Int) {
        viewModelScope.launch {
            val newItem = Item(name = name, latitude = latitude, longitude = longitude, id=id)
            repository.addItem(newItem)
            // Refresh list after addition
            fetchItems()

        }
    }

    fun updateItem(itemId: Int, name: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val updatedItem = Item(id = itemId, name = name, latitude = latitude, longitude = longitude)
            repository.updateItemDetails(updatedItem)
            // Refresh list after update
            fetchItems()
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item)
            // Refresh list after deletion
            fetchItems()
        }
    }

    fun setSelectedItemId(itemId: Int) {
        _selectedItemId.value = itemId
    }



}