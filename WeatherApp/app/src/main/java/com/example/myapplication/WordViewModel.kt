package com.example.myapplication

import android.app.Application
import android.app.DownloadManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Database.CityModel
import com.example.myapplication.Database.DetailModel
import com.example.myapplication.Database.WordRoomDatabase
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    val allWords: LiveData<List<CityModel>>
    val getone : CityModel
    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
        getone = repository.getone
    }
    fun insert(model: CityModel) = viewModelScope.launch {
        repository.insert(model)
    }
    fun countRow() : Int
    {
        return repository.countRow()
    }
    fun delete(model: CityModel)
    {
        repository.delete(model)
    }
    fun getCityModel(cityModel: String) : Boolean
    {
        return repository.getCityModel(cityModel)
    }
    fun getSearch(query: String) : ArrayList<CityModel>
    {
        return repository.getSearch(query)
    }
    fun getDeatail(query: String) : DetailModel
    {
        return repository.getDatail(query)
    }

}