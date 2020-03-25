package com.example.myapplication

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Database.CityModel
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

class Main2Activity() : AppCompatActivity(), Parcelable {

    lateinit var temp_C : TextView
    lateinit var temp_F : TextView
    lateinit var hudimy : TextView
    lateinit var weatherDesc : TextView
    lateinit var image : ImageView
    lateinit var url : TextView
    lateinit var city : TextView
    lateinit var query : String

    lateinit var cityModel: CityModel

    private lateinit var wordViewModel: WordViewModel

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val Intent = getIntent()

        temp_C = findViewById(R.id.temp_C)
        temp_F = findViewById(R.id.temp_F)
        hudimy = findViewById(R.id.humidity)
        weatherDesc = findViewById(R.id.weatherDesc)
        url = findViewById(R.id.url)
        image = findViewById(R.id.image)
        city = findViewById(R.id.city)
        cityModel  = Intent.getParcelableExtra<CityModel>("weather1")
        query = cityModel.City.toString()
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        clickHistory()
        clickSearch()
    }

    fun clickHistory()
    {
        city.setText(cityModel.City.toString())
        wordViewModel.delete(cityModel)
        wordViewModel.insert(cityModel)
        val DetailHistory = wordViewModel.getDeatail("http://api.worldweatheronline.com/premium/v1/weather.ashx?format=json&key=6fe0e24c40354453beb152141202702&query="+query+"")
        temp_C.setText(DetailHistory.tempC.toString())
        weatherDesc.setText(DetailHistory.weatherDesc.toString())
        Picasso.get().load(DetailHistory.weatherIconUrl.toString()).into(image)
        hudimy.setText(DetailHistory.humidity)
    }

    fun clickSearch()
    {
        city.setText(cityModel.City.toString())
        var count : Int = wordViewModel.countRow()
        if (count<3)
        {
            wordViewModel.insert(cityModel)
        }
        else
        {
            val isValid = wordViewModel.getCityModel(cityModel.City.toString())
            if (isValid==false)
            {
                wordViewModel.delete(cityModel)
                wordViewModel.insert(cityModel)

            }
            else
            {
                wordViewModel.delete(wordViewModel.getone)
                wordViewModel.insert(cityModel)

            }
        }
        val DetailSearch = wordViewModel.getDeatail("http://api.worldweatheronline.com/premium/v1/weather.ashx?format=json&key=6fe0e24c40354453beb152141202702&query="+query+"")
        temp_C.setText(DetailSearch.tempC.toString())
        weatherDesc.setText(DetailSearch.weatherDesc.toString())
        Picasso.get().load(DetailSearch.weatherIconUrl.toString()).into(image)
        hudimy.setText(DetailSearch.humidity)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Main2Activity> {
        override fun createFromParcel(parcel: Parcel): Main2Activity {
            return Main2Activity(parcel)
        }

        override fun newArray(size: Int): Array<Main2Activity?> {
            return arrayOfNulls(size)
        }
    }

}
