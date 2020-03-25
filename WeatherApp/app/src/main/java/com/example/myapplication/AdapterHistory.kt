package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.CityModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.item_recycleview.view.*
import java.lang.reflect.Type

class AdapterHistory internal constructor(var models: List<CityModel>) : RecyclerView.Adapter<AdapterHistory.MessageViewHolder>() {

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id = itemView.findViewById<TextView>(R.id.textView2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val Context : Context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycleview, parent, false)
        val MessageViewHolder = MessageViewHolder(itemView)

        if(MessageViewHolder.itemView.linearitem!=null)
        {
            MessageViewHolder.itemView.setOnClickListener {
                var Model : CityModel = models.get(MessageViewHolder.adapterPosition)
                val Intent = Intent(Context,Main2Activity::class.java)
                Intent.putExtra("weather1",Model)
                Context.startActivity(Intent)
            }
        }
        return MessageViewHolder
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val current = models[position]
        holder.id.text = current.City
    }

    override fun getItemCount() = models.count()

    internal fun setWords(models: List<CityModel>) {
        this.models=models
        notifyDataSetChanged()
    }
}