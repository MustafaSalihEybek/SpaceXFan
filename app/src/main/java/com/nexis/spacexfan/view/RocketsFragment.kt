package com.nexis.spacexfan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexis.spacexfan.adapter.RocketsAdapter
import com.nexis.spacexfan.adapter.decoration.LinearManagerDecoration
import com.nexis.spacexfan.databinding.FragmentRocketsBinding
import com.nexis.spacexfan.model.Rocket
import com.nexis.spacexfan.util.Singleton
import com.nexis.spacexfan.util.show
import com.nexis.spacexfan.viewmodel.RocketsViewModel

class RocketsFragment(val userId: String?) : Fragment() {
    private lateinit var v: View
    private lateinit var rocketsBinding: FragmentRocketsBinding
    private lateinit var rocketsViewModel: RocketsViewModel

    private lateinit var rocketsAdapter: RocketsAdapter
    private lateinit var rocketList: ArrayList<Rocket>

    private fun init(){
        rocketsBinding.rocketsFragmentRecyclerView.setHasFixedSize(true)
        rocketsBinding.rocketsFragmentRecyclerView.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
        rocketsAdapter = RocketsAdapter(arrayListOf(), v, userId)
        rocketsBinding.rocketsFragmentRecyclerView.adapter = rocketsAdapter

        rocketsViewModel = ViewModelProvider(this).get(RocketsViewModel::class.java)
        observeLiveData()
        rocketsViewModel.getRockets()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rocketsBinding = FragmentRocketsBinding.inflate(inflater, container, false)
        return rocketsBinding.root
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

                if (rocketsBinding.rocketsFragmentRecyclerView.itemDecorationCount > 0)
                    rocketsBinding.rocketsFragmentRecyclerView.removeItemDecorationAt(0)

                rocketsBinding.rocketsFragmentRecyclerView.addItemDecoration(LinearManagerDecoration(Singleton.V_SIZE, it.size))
                rocketsAdapter.loadData(rocketList)
            }
        })

        rocketsViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.show(v, it)
            }
        })
    }
}