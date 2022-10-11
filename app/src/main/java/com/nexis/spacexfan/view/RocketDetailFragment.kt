package com.nexis.spacexfan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class RocketDetailFragment : Fragment(), View.OnClickListener {
    private lateinit var v: View
    private lateinit var detailBinding: FragmentRocketDetailBinding

    private lateinit var rocketsDetailsAdapter: RocketsDetailsAdapter
    private lateinit var rocketDetailList: ArrayList<RocketDetail>
    private lateinit var rocketData: Rocket

    private lateinit var rocketImagesList: ArrayList<String>
    private lateinit var rocketsImagesAdapter: RocketsImagesAdapter

    private fun init(){
        arguments?.let {
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

            detailBinding.rocketDetailFragmentImgBack.setOnClickListener(this)
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

    override fun onClick(p0: View?) {
        p0?.let {
            when (it.id){
                R.id.rocket_detail_fragment_imgBack -> Navigation.findNavController(v).popBackStack()
                else -> return
            }
        }
    }
}