package com.example.helldivers2companionapp.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.helldivers2companionapp.R
import com.example.helldivers2companionapp.data.model.User

class SquadAdapter(private val users: List<User>) :
    RecyclerView.Adapter<SquadAdapter.SquadViewHolder>() {

    class SquadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvStats: TextView = view.findViewById(R.id.tvStats)
        val btnRecruit: Button = view.findViewById(R.id.btnRecruit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadViewHolder {
        // Ganti 'item_card_squad' menjadi 'item_card_estetic' sesuai nama filemu
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_estetic, parent, false)
        return SquadViewHolder(view)
    }

    override fun onBindViewHolder(holder: SquadViewHolder, position: Int) {
        val user = users[position]
        holder.tvName.text = user.username.uppercase()
        holder.tvStats.text = "Confirmed Kills: ${user.kills} | Missions: ${user.missionsCompleted}"

        // Logika Tombol Recruit
        // Di onBindViewHolder SquadAdapter
        holder.btnRecruit.setOnClickListener {
            if (holder.btnRecruit.text == "RECRUIT") {
                holder.btnRecruit.text = "IN SQUAD"
                holder.btnRecruit.setBackgroundColor(android.graphics.Color.DKGRAY)
                Toast.makeText(holder.itemView.context, "${user.username} Joined Your Squad!", Toast.LENGTH_SHORT).show()
            } else {
                holder.btnRecruit.text = "RECRUIT"
                holder.btnRecruit.setBackgroundColor(android.graphics.Color.YELLOW) // Warna primer kamu
            }
        }
    }

    override fun getItemCount() = users.size
}