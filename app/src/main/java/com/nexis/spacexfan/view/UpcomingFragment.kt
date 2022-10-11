package com.nexis.spacexfan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexis.spacexfan.R
import com.nexis.spacexfan.adapter.UpComingAdapter
import com.nexis.spacexfan.adapter.decoration.LinearManagerDecoration
import com.nexis.spacexfan.databinding.FragmentUpcomingBinding
import com.nexis.spacexfan.databinding.UpcomingItemBinding
import com.nexis.spacexfan.model.Upcoming
import com.nexis.spacexfan.util.Singleton
import com.nexis.spacexfan.util.show
import com.nexis.spacexfan.viewmodel.UpComingViewModel

class UpcomingFragment : Fragment() {
    private lateinit var v: View
    private lateinit var upComingBinding: FragmentUpcomingBinding
    private lateinit var upComingViewModel: UpComingViewModel

    private lateinit var upComingAdapter: UpComingAdapter
    private lateinit var upComingList: ArrayList<Upcoming>

    private fun init(){
        upComingBinding.upcomingFragmentRecyclerView.setHasFixedSize(true)
        upComingBinding.upcomingFragmentRecyclerView.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
        upComingAdapter = UpComingAdapter(arrayListOf())
        upComingBinding.upcomingFragmentRecyclerView.adapter = upComingAdapter

        upComingViewModel = ViewModelProvider(this).get(UpComingViewModel::class.java)
        observeLiveData()
        upComingViewModel.getUpComing()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        upComingBinding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return upComingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()
    }

    private fun observeLiveData(){
        upComingViewModel.upcomingList.observe(viewLifecycleOwner, Observer {
            it?.let {
                upComingList = it

                if (upComingBinding.upcomingFragmentRecyclerView.itemDecorationCount > 0)
                    upComingBinding.upcomingFragmentRecyclerView.removeItemDecorationAt(0)

                upComingBinding.upcomingFragmentRecyclerView.addItemDecoration(LinearManagerDecoration(Singleton.V_SIZE, it.size))
                upComingAdapter.loadData(upComingList)
            }
        })

        upComingViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.show(v, it)
            }
        })
    }
}