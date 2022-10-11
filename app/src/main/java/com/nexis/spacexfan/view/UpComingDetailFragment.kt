package com.nexis.spacexfan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.nexis.spacexfan.databinding.FragmentUpComingDetailBinding
import com.nexis.spacexfan.model.Upcoming

class UpComingDetailFragment : Fragment() {
    private lateinit var v: View
    private lateinit var upComingDetailBinding: FragmentUpComingDetailBinding

    private lateinit var upComingData: Upcoming

    private fun init(){
        arguments?.let {
            upComingData = UpComingDetailFragmentArgs.fromBundle(it).upComingData
            upComingDetailBinding.upcoming = upComingData

            upComingDetailBinding.upComingDetailFragmentImgBack.setOnClickListener {
                Navigation.findNavController(v).popBackStack()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        upComingDetailBinding = FragmentUpComingDetailBinding.inflate(inflater, container, false)
        return upComingDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()
    }
}