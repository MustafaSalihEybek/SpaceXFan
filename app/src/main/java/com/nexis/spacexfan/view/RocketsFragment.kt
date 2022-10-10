package com.nexis.spacexfan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nexis.spacexfan.R
import com.nexis.spacexfan.model.Rocket.Rocket
import com.nexis.spacexfan.util.show
import com.nexis.spacexfan.viewmodel.RocketsViewModel

class RocketsFragment : Fragment() {
    private lateinit var v: View
    private lateinit var rocketsViewModel: RocketsViewModel

    private lateinit var rocketList: ArrayList<Rocket>

    private fun init(){
        rocketsViewModel = ViewModelProvider(this).get(RocketsViewModel::class.java)
        observeLiveData()
        rocketsViewModel.getRockets()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rockets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()
    }

    private fun observeLiveData(){
        rocketsViewModel.rocketList.observe(viewLifecycleOwner, Observer {
            it?.let {
                rocketList = it
                println("Size: ${rocketList.size}")
            }
        })

        rocketsViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                println("Error: ${it}")
            }
        })
    }
}