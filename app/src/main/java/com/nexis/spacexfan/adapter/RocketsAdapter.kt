package com.nexis.spacexfan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nexis.spacexfan.databinding.RocketItemBinding
import com.nexis.spacexfan.model.Rocket
import com.nexis.spacexfan.view.MainFragmentDirections

class RocketsAdapter(var rocketList: ArrayList<Rocket>, val vV: View) : RecyclerView.Adapter<RocketsAdapter.RocketsHolder>() {
    private lateinit var v: RocketItemBinding
    private lateinit var navDirections: NavDirections
    private var aPos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsHolder {
        v = RocketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketsHolder(v)
    }

    override fun onBindViewHolder(holder: RocketsHolder, position: Int) {
        holder.rI.rocket = rocketList.get(position)

        holder.itemView.setOnClickListener {
            aPos = holder.adapterPosition

            if (aPos != RecyclerView.NO_POSITION)
                goToRocketDetailPage(rocketList.get(aPos))
        }
    }

    override fun getItemCount() = rocketList.size

    inner class RocketsHolder(val rI: RocketItemBinding) : RecyclerView.ViewHolder(rI.root)

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(rockets: ArrayList<Rocket>){
        rocketList = rockets
        notifyDataSetChanged()
    }

    private fun goToRocketDetailPage(rocket: Rocket){
        navDirections = MainFragmentDirections.actionMainFragmentToRocketDetailFragment(rocket)
        Navigation.findNavController(vV).navigate(navDirections)
    }
}