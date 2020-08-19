package com.example.notter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notter.data.models.NotterData

@Database(entities = [NotterData::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class NotterDatabase : RoomDatabase() {

    abstract fun notterDao(): NotterDao

    companion object {

        @Volatile
        private var INSTANCE: NotterDatabase? = null

        fun getDatabase(context: Context): NotterDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotterDatabase::class.java,
                    "notter_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}