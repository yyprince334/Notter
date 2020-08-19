package com.example.notter.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notter.data.models.NotterData

@Dao
interface NotterDao {

    @Query("SELECT * FROM notter_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<NotterData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(notterData: NotterData)

    @Update
    suspend fun updateData(notterData: NotterData)

    @Delete
    suspend fun deleteItem(notterData: NotterData)

    @Query("DELETE FROM notter_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM notter_table WHERE title LIKE :searchQuery ")
    fun searchDatabase(searchQuery: String):LiveData<List<NotterData>>

    @Query("SELECT * FROM notter_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<NotterData>>

    @Query("SELECT * FROM notter_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<NotterData>>
}