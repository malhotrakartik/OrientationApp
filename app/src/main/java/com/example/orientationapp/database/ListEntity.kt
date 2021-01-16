package com.example.orientationapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Lists")
data class ListEntity(

    @PrimaryKey val listId: String,
    @ColumnInfo(name = "list_roll") val listRoll : String,
    @ColumnInfo(name = "list_pitch") val listPitch: String

//    @ColumnInfo(name = "restaurant_image") val resImage : String


)