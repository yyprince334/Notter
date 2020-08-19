package com.example.notter.data.viewModel

import android.app.Application
import android.app.DownloadManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notter.data.NotterDatabase
import com.example.notter.data.models.NotterData
import com.example.notter.data.repository.NotterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotterViewModel(application: Application) : AndroidViewModel(application) {

    private val notterDao = NotterDatabase.getDatabase(
        application
    ).notterDao()
    private val repository: NotterRepository
    val getAllData: LiveData<List<NotterData>>
    val sortByHighPriority: LiveData<List<NotterData>>
    val sortByLowPriority: LiveData<List<NotterData>>

    init {
        repository = NotterRepository(notterDao)
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun insertData(notterData: NotterData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(notterData)
        }
    }

    fun updateData(notterData: NotterData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(notterData)
        }
    }

    fun deleteItem(notterData: NotterData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(notterData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String):LiveData<List<NotterData>>{
        return repository.searchDatabase(searchQuery)
    }

}