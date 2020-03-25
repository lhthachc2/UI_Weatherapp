package com.example.myapplication

import android.app.DownloadManager
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Database.CityModel
import com.example.myapplication.Database.DetailModel
import com.example.myapplication.Database.WordDao
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.MalformedURLException
import java.net.URL

class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<CityModel>> = wordDao.getAlphabetizedWords()
    val getone : CityModel = wordDao.getone()

    suspend fun insert(model: CityModel) {
        wordDao.insert(model)
    }
    fun delete(model: CityModel){
        wordDao.delete(model)
    }
    fun countRow() : Int
    {
        var count: Int = wordDao.countRow()
        return count
    }
    fun getCityModel(cityModel : String) : Boolean
    {
       val cityModel = wordDao.getcityModel(cityModel)
        if (cityModel == null) // Không có
            return true
        else // Có
            return false
    }
    suspend fun getResponse(query: String) : String
    {
        val response = getAsync(query).await()
        return response
    }
    fun getSearch(query: String) = runBlocking {
        val arrayList: Deferred<ArrayList<CityModel>> = async { jsonSearchWeather(query)  }
        return@runBlocking arrayList.await()
    }

    fun getDatail(query: String) = runBlocking {
        val arrayList: Deferred<DetailModel> = async { jsonDetailWeather(query)  }
        return@runBlocking arrayList.await()
    }

    fun getAsync(url: String) = CoroutineScope(Dispatchers.IO).async{
        val content = StringBuilder()
        try {
            val url = URL(url)
            val inputStreamReader = InputStreamReader(url.openConnection().getInputStream())
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String? = ""
            while ({ line = bufferedReader.readLine(); line }() != null) {
                content.append(line)
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return@async content.toString()
    }

    suspend fun jsonSearchWeather(json : String) : ArrayList<CityModel>
    {
        val a : String = getResponse(json)
        var arraylist = ArrayList<CityModel>()
        try {
            val json = JSONObject(a)
            val jsonObject = json.getJSONObject("search_api")
            val jsonArray = jsonObject.getJSONArray("result")
            for (i in 0..jsonArray.length()-1) {
                val JSONObject = jsonArray.getJSONObject(i)
                val JSONArrayarea = JSONObject.getJSONArray("areaName")
                for (j in 0..JSONArrayarea.length()-1 )
                {
                    val JSONObjectvalue = JSONArrayarea.getJSONObject(j)
                    var value : String = JSONObjectvalue.getString("value")
                    arraylist.add(CityModel(value))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return  arraylist
    }

    suspend fun jsonDetailWeather(json: String) : DetailModel
    {
        lateinit var Detail : DetailModel
        val a : String = getResponse(json)
        try
        {
            val json = JSONObject(a)
            val jsonObject = json.getJSONObject("data")
            val jsonArray = jsonObject.getJSONArray("current_condition")
            for (i in 0..jsonArray.length()-1)
            {
                val JSONObject = jsonArray.getJSONObject(i)
                val temC = JSONObject.getString("temp_C")
                val weatherDesc = JSONObject.getJSONArray("weatherDesc").getJSONObject(0).getString("value")
                val weatherIconUrl = JSONObject.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value")
                val hudimy = JSONObject.getString("humidity")
                Detail = DetailModel(temC,weatherDesc,hudimy,weatherIconUrl)
            }
        }
        catch (e: IOException) {
            e.printStackTrace()
        }
        return Detail
    }

}