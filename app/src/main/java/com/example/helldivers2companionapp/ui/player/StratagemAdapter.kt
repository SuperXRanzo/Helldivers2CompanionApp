package com.example.helldivers2companionapp.ui.player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helldivers2companionapp.data.model.Stratagem
import com.example.helldivers2companionapp.databinding.ItemStratagemBinding

class StratagemAdapter(private var stratagems: List<Stratagem>) :
    RecyclerView.Adapter<StratagemAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemStratagemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStratagemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stratagems[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvType.text = "Type: ${item.category}"
        holder.binding.tvCode.text = item.inputCode
    }

    override fun getItemCount() = stratagems.size

    fun updateData(newList: List<Stratagem>) {
        stratagems = newList
        notifyDataSetChanged()
    }
}