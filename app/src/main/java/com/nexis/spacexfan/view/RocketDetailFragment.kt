package com.nexis.spacexfan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexis.spacexfan.R
import com.nexis.spacexfan.adapter.RocketsDetailsAdapter
import com.nexis.spacexfan.adapter.RocketsImagesAdapter
import com.nexis.spacexfan.adapter.decoration.GridManagerDecoration
import com.nexis.spacexfan.databinding.FragmentRocketDetailBinding
import com.nexis.spacexfan.model.Rocket
import com.nexis.spacexfan.model.RocketDetail
import com.nexis.spacexfan.util.Singleton
import com.nexis.spacexfan.util.show
import com.nexis.spacexfan.viewmodel.RocketDetailViewModel

class RocketDetailFragment : Fragment(), View.OnClickListener {
    private lateinit var v: View
    private lateinit var detailBinding: FragmentRocketDetailBinding
    private lateinit var rocketDetailViewModel: RocketDetailViewModel

    private lateinit var rocketsDetailsAdapter: RocketsDetailsAdapter
    private lateinit var rocketDetailList: ArrayList<RocketDetail>
    private lateinit var rocketData: Rocket

    private lateinit var rocketImagesList: ArrayList<String>
    private lateinit var rocketsImagesAdapter: RocketsImagesAdapter

    private var userId: String? = null

    private fun init(){
        arguments?.let {
            userId = RocketDetailFragmentArgs.fromBundle(it).userId
            rocketData = RocketDetailFragmentArgs.fromBundle(it).rocketData
            detailBinding.rocket = rocketData

            rocketDetailList = ArrayList()
            rocketImagesList = ArrayList()

            rocketDetailList.add(RocketDetail(
                "height",
                "${rocketData.height.meters}m / ${rocketData.height.feet} ft"
            ))
            rocketDetailList.add(RocketDetail(
                "diameter",
                "${rocketData.diameter.meters}m / ${rocketData.diameter.feet} ft"
            ))
            rocketDetailList.add(RocketDetail(
                "mass",
                "${rocketData.mass.kg} kg / ${rocketData.mass.lb} lb"
            ))

            for (payload in rocketData.payload_weights){
                rocketDetailList.add(RocketDetail(
                    "payload to ${payload.id}",
                    "${payload.kg} kg / ${payload.lb} lb"
                ))
            }

            for (image in rocketData.flickr_images)
                rocketImagesList.add(image)

            detailBinding.rocketDetailFragmentRecyclerViewDetails.setHasFixedSize(true)
            detailBinding.rocketDetailFragmentRecyclerViewDetails.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
            rocketsDetailsAdapter = RocketsDetailsAdapter(rocketDetailList)
            detailBinding.rocketDetailFragmentRecyclerViewDetails.adapter = rocketsDetailsAdapter

            detailBinding.rocketDetailFragmentRecyclerViewOtherImages.setHasFixedSize(true)
            detailBinding.rocketDetailFragmentRecyclerViewOtherImages.layoutManager = GridLayoutManager(v.context, 2)
            rocketsImagesAdapter = RocketsImagesAdapter(rocketImagesList)
            detailBinding.rocketDetailFragmentRecyclerViewOtherImages.addItemDecoration(GridManagerDecoration(Singleton.V_SIZE, Singleton.H_SIZE))
            detailBinding.rocketDetailFragmentRecyclerViewOtherImages.adapter = rocketsImagesAdapter

            rocketDetailViewModel = ViewModelProvider(this).get(RocketDetailViewModel::class.java)
            observeLiveData()

            if (userId != null && rocketData.id != null)
                rocketDetailViewModel.checkFavorite(userId!!, rocketData.id!!)
            else if (rocketData.id != null)
                detailBinding.rocketDetailFragmentImgAddFavorite.visibility = View.VISIBLE

            detailBinding.rocketDetailFragmentImgBack.setOnClickListener(this)
            detailBinding.rocketDetailFragmentImgAddFavorite.setOnClickListener(this)
            detailBinding.rocketDetailFragmentImgRemoveFavorite.setOnClickListener(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentRocketDetailBinding.inflate(inflater, container, false)
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()
    }

    private fun observeLiveData(){
        rocketDetailViewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    detailBinding.rocketDetailFragmentImgRemoveFavorite.visibility = View.VISIBLE
                    detailBinding.rocketDetailFragmentImgAddFavorite.visibility = View.GONE
                } else {
                    detailBinding.rocketDetailFragmentImgAddFavorite.visibility = View.VISIBLE
                    detailBinding.rocketDetailFragmentImgRemoveFavorite.visibility = View.GONE
                }
            }
        })

        rocketDetailViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.show(v, it)
            }
        })

        rocketDetailViewModel.successMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.show(v, it)
            }
        })
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when (it.id){
                R.id.rocket_detail_fragment_imgBack -> Navigation.findNavController(v).popBackStack()
                R.id.rocket_detail_fragment_imgAddFavorite -> addOrRemoveFavorite(true)
                R.id.rocket_detail_fragment_imgRemoveFavorite -> addOrRemoveFavorite(false)
                else -> return
            }
        }
    }

    private fun addOrRemoveFavorite(isAdd: Boolean){
        if (userId != null){
            if (rocketData.id != null){
                if (isAdd)
                    rocketDetailViewModel.addFavorite(userId!!, rocketData.id!!)
                else
                    rocketDetailViewModel.removeFavorite(userId!!, rocketData.id!!)
            } else
                "message".show(v, "Rocket data id not found")
        } else
            "message".show(v, "You must be logged in to do this!")
    }
}