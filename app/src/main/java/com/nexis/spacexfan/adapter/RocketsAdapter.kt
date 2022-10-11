package com.nexis.spacexfan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nexis.spacexfan.databinding.RocketItemBinding
import com.nexis.spacexfan.model.Favorite
import com.nexis.spacexfan.model.Rocket
import com.nexis.spacexfan.util.AppUtil
import com.nexis.spacexfan.util.FirebaseUtil
import com.nexis.spacexfan.util.Singleton
import com.nexis.spacexfan.util.show
import com.nexis.spacexfan.view.MainFragmentDirections

class RocketsAdapter(var rocketList: ArrayList<Rocket>, val vV: View?, val userId: String?) : RecyclerView.Adapter<RocketsAdapter.RocketsHolder>() {
    private lateinit var v: RocketItemBinding
    private lateinit var navDirections: NavDirections
    private var aPos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsHolder {
        v = RocketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketsHolder(v)
    }

    override fun onBindViewHolder(holder: RocketsHolder, position: Int) {
        holder.rI.rocket = rocketList.get(position)

        holder.rI.rocketItemImgRocket.setOnClickListener {
            aPos = holder.adapterPosition

            if (aPos != RecyclerView.NO_POSITION)
                goToRocketDetailPage(rocketList.get(aPos))
        }

        if (userId != null){
            FirebaseUtil.checkFavorite(userId, rocketList.get(position).id!!, checkFavoriteListener = {isFavorite, onError ->
                if (!Singleton.isLogout){
                    onError?.let {
                        if (vV != null)
                            it.show(vV, it)
                    }

                    if (isFavorite){
                        holder.rI.rocketItemImgRemoveFavorite.visibility = View.VISIBLE
                        holder.rI.rocketItemImgAddFavorite.visibility = View.GONE
                    } else {
                        holder.rI.rocketItemImgAddFavorite.visibility = View.VISIBLE
                        holder.rI.rocketItemImgRemoveFavorite.visibility = View.GONE
                    }
                }
            })
        } else
            holder.rI.rocketItemImgAddFavorite.visibility = View.VISIBLE

        holder.rI.rocketItemImgAddFavorite.setOnClickListener {
            aPos = holder.adapterPosition

            if (aPos != RecyclerView.NO_POSITION){
                if (userId != null){
                    AppUtil.mFavorite = Favorite(rocketList.get(aPos).id!!)

                    FirebaseUtil.addFavorite(userId, AppUtil.mFavorite, addFavoriteOnComplete = {onMessage ->
                        onMessage?.let {
                            if (vV != null)
                                it.show(vV, it)
                        }
                    })
                } else {
                    if (vV != null)
                        "message".show(vV, "You must be logged in to do this!")
                }
            }
        }

        holder.rI.rocketItemImgRemoveFavorite.setOnClickListener {
            aPos = holder.adapterPosition

            if (aPos != RecyclerView.NO_POSITION){
                if (userId != null){
                    FirebaseUtil.removeFavorite(userId, rocketList.get(aPos).id!!, removeFavoriteOnComplete = {onMessage ->
                        onMessage?.let {
                            if (vV != null)
                                it.show(vV, it)
                        }
                    })
                } else {
                    if (vV != null)
                        "message".show(vV, "You must be logged in to do this!")
                }
            }
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
        if (vV != null) {
            navDirections = MainFragmentDirections.actionMainFragmentToRocketDetailFragment(rocket, userId)
            Navigation.findNavController(vV).navigate(navDirections)
        }
    }
}