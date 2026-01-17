package com.example.helldivers2companionapp.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.helldivers2companionapp.R
import com.example.helldivers2companionapp.data.model.Mission

class MissionAdapter(private val missions: List<Mission>) :
    RecyclerView.Adapter<MissionAdapter.MissionViewHolder>() {

    class MissionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Samakan dengan ID di item_card_estetic.xml kamu
        val tvPlanet: TextView = view.findViewById(R.id.tvName)
        val tvDetails: TextView = view.findViewById(R.id.tvStats)
        val btnAction: Button = view.findViewById(R.id.btnRecruit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_estetic, parent, false)
        return MissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: MissionViewHolder, position: Int) {
        val mission = missions[position]

        holder.tvPlanet.text = "PLANET: ${mission.planetName.uppercase()}"
        holder.tvDetails.text = "Type: ${mission.type} | Reward: ${mission.reward} Medals"

        // Karena ini list misi, kita ubah teks tombolnya bukan 'RECRUIT'
        holder.btnAction.text = "VIEW"
    }

    override fun getItemCount() = missions.size
}