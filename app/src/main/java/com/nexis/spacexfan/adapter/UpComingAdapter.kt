package com.nexis.spacexfan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexis.spacexfan.databinding.UpcomingItemBinding
import com.nexis.spacexfan.model.Upcoming

class UpComingAdapter(var upComingList: ArrayList<Upcoming>) : RecyclerView.Adapter<UpComingAdapter.UpComingHolder>() {
    private lateinit var v: UpcomingItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingHolder {
        v = UpcomingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpComingHolder(v)
    }

    override fun onBindViewHolder(holder: UpComingHolder, position: Int) {
        holder.uI.upcoming = upComingList.get(position)
    }

    override fun getItemCount() = upComingList.size

    inner class UpComingHolder(val uI: UpcomingItemBinding) : RecyclerView.ViewHolder(uI.root)

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(upcomings: ArrayList<Upcoming>){
        upComingList = upcomings
        notifyDataSetChanged()
    }
}