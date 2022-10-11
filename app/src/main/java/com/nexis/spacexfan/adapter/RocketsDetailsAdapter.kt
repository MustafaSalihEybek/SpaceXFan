package com.nexis.spacexfan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexis.spacexfan.databinding.RocketDetailItemBinding
import com.nexis.spacexfan.model.RocketDetail

class RocketsDetailsAdapter(val rocketDetailList: ArrayList<RocketDetail>) : RecyclerView.Adapter<RocketsDetailsAdapter.RocketsDetailsHolder>() {
    private lateinit var v: RocketDetailItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsDetailsHolder {
        v = RocketDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketsDetailsHolder(v)
    }

    override fun onBindViewHolder(holder: RocketsDetailsHolder, position: Int) {
        holder.rD.rocketdetail = rocketDetailList.get(position)
    }

    override fun getItemCount() = rocketDetailList.size

    inner class RocketsDetailsHolder(val rD: RocketDetailItemBinding) : RecyclerView.ViewHolder(rD.root)
}