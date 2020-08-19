package com.example.notter.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notter_table")
@Parcelize
data class NotterData(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val priority: Priority,
    val description: String

): Parcelable