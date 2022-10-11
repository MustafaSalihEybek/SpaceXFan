package com.nexis.spacexfan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexis.spacexfan.databinding.RocketDetailImgItemBinding
import com.nexis.spacexfan.util.downloadImageUrl

class RocketsImagesAdapter(val rocketImageList: ArrayList<String>) : RecyclerView.Adapter<RocketsImagesAdapter.RocketsImagesHolder>() {
    private lateinit var v: RocketDetailImgItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsImagesHolder {
        v = RocketDetailImgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketsImagesHolder(v)
    }

    override fun onBindViewHolder(holder: RocketsImagesHolder, position: Int) {
        holder.rI.rocketDetailImgItemRocketImg.downloadImageUrl(rocketImageList.get(position))
    }

    override fun getItemCount() = rocketImageList.size

    inner class RocketsImagesHolder(val rI: RocketDetailImgItemBinding) : RecyclerView.ViewHolder(rI.root)
}