package com.example.myapplication.Database

import android.graphics.ColorSpace
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {

    @Query("SELECT * FROM city_table LIMIT 10")
    fun getAlphabetizedWords(): LiveData<List<CityModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(model: CityModel)

    @Query("SELECT count(*) FROM city_table ")
    fun countRow() : Int

    @Delete
    fun delete (model: CityModel)

    @Query("SELECT * FROM city_table LIMIT 1")
    fun getone() : CityModel

    @Query("SELECT * FROM city_table WHERE city LIKE :cityModel ")
    fun getcityModel(cityModel: String) : CityModel
}