package com.example.myapplication.Database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailModel(
    val tempC : String,
    val weatherDesc : String,
    val humidity: String,
    val weatherIconUrl: String
): Parcelable