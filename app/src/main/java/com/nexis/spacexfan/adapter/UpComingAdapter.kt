package com.nexis.spacexfan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nexis.spacexfan.databinding.UpcomingItemBinding
import com.nexis.spacexfan.model.Upcoming
import com.nexis.spacexfan.view.MainFragmentDirections

class UpComingAdapter(var upComingList: ArrayList<Upcoming>, val vV: View) : RecyclerView.Adapter<UpComingAdapter.UpComingHolder>() {
    private lateinit var v: UpcomingItemBinding
    private lateinit var navDirections: NavDirections
    private var aPos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingHolder {
        v = UpcomingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpComingHolder(v)
    }

    override fun onBindViewHolder(holder: UpComingHolder, position: Int) {
        holder.uI.upcoming = upComingList.get(position)

        holder.itemView.setOnClickListener {
            aPos = holder.adapterPosition

            if (aPos != RecyclerView.NO_POSITION)
                goToUpComingDetailPage(upComingList.get(aPos))
        }
    }

    override fun getItemCount() = upComingList.size

    inner class UpComingHolder(val uI: UpcomingItemBinding) : RecyclerView.ViewHolder(uI.root)

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(upcomings: ArrayList<Upcoming>){
        upComingList = upcomings
        notifyDataSetChanged()
    }

    private fun goToUpComingDetailPage(upComing: Upcoming){
        navDirections = MainFragmentDirections.actionMainFragmentToUpComingDetailFragment(upComing)
        Navigation.findNavController(vV).navigate(navDirections)
    }
}