package com.superprof.tumbuhan.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.superprof.tumbuhan.databinding.ItemPlantBinding
import com.superprof.tumbuhan.domain.Plant

class PlantAdapter(val context: Context, var dataList: List<Plant>, val onPlantClicked:(data:Plant)->Unit) :
    RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(val binding: ItemPlantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Plant) {
            Glide.with(context).load(data.imageUrl).into(binding.ivPlant)
            binding.tvName.text = data.name
            binding.root.setOnClickListener { onPlantClicked.invoke(data) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = ItemPlantBinding.inflate(LayoutInflater.from(context), parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun notifyDataUpdated(newData: List<Plant>){
        dataList = newData
        notifyDataSetChanged()
    }
}
