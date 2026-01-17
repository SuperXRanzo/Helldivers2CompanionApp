package com.example.helldivers2companionapp.ui.player

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.helldivers2companionapp.R
import com.example.helldivers2companionapp.data.model.Loadout

class LoadoutAdapter(private var loadouts: List<Loadout>) :
    RecyclerView.Adapter<LoadoutAdapter.LoadoutViewHolder>() {

    class LoadoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvDetails: TextView = view.findViewById(R.id.tvStats)
        val btnEquip: View = view.findViewById(R.id.btnRecruit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_estetic, parent, false)
        return LoadoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoadoutViewHolder, position: Int) {
        val loadout = loadouts[position]

        // Nama Loadout
        holder.tvName.text = loadout.name.uppercase()

        // Detail Senjata & Stratagem sesuai variabel di Loadout.kt
        holder.tvDetails.text = """
            Weapon: ${loadout.primaryWeapon} | ${loadout.secondaryWeapon}
            Stratagems: ${loadout.stratagem1}, ${loadout.stratagem2}
        """.trimIndent()

        if (holder.btnEquip is Button) {
            holder.btnEquip.text = "EQUIP"
            holder.btnEquip.setBackgroundColor(Color.parseColor("#FFD700"))
            holder.btnEquip.setTextColor(Color.BLACK)

            holder.btnEquip.setOnClickListener {
                Toast.makeText(holder.itemView.context, "Loadout Active!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount() = loadouts.size

    fun updateData(newList: List<Loadout>) {
        loadouts = newList
        notifyDataSetChanged()
    }
}