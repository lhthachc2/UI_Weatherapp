package com.example.myapplication.Database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "city_table")
data class CityModel(@PrimaryKey @ColumnInfo(name = "city") var City: String) :  Parcelable