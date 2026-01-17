package com.example.helldivers2companionapp.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helldivers2companionapp.R
import com.example.helldivers2companionapp.data.model.WarStatus

class WarStatusAdapter(private val list: List<WarStatus>) :
    RecyclerView.Adapter<WarStatusAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlanet: TextView = view.findViewById(android.R.id.text1)
        val tvProgress: TextView = view.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Menggunakan layout bawaan android yang simpel: simple_list_item_2
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvPlanet.text = item.planetName
        holder.tvPlanet.setTextColor(android.graphics.Color.YELLOW) // Warna khas Helldivers
        holder.tvProgress.text = "Liberation: ${item.liberationProgress}%"
        holder.tvProgress.setTextColor(android.graphics.Color.WHITE)
    }

    override fun getItemCount() = list.size
}