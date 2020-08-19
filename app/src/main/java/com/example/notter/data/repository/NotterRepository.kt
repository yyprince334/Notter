package com.example.notter.data.repository

import androidx.lifecycle.LiveData
import com.example.notter.data.NotterDao
import com.example.notter.data.models.NotterData

class NotterRepository(private val notterDao: NotterDao) {

    val getAllData: LiveData<List<NotterData>> = notterDao.getAllData()
    val sortByHighPriority: LiveData<List<NotterData>> = notterDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<NotterData>> = notterDao.sortByLowPriority()


    suspend fun insertData(notterData: NotterData) {
        notterDao.insertData(notterData)
    }

    suspend fun updateData(notterData: NotterData){
        notterDao.updateData(notterData)
    }

    suspend fun deleteItem(notterData: NotterData){
        notterDao.deleteItem(notterData)
    }

    suspend fun deleteAll(){
        notterDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<NotterData>>{
        return notterDao.searchDatabase(searchQuery)
    }
}