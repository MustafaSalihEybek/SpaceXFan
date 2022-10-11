package com.nexis.spacexfan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nexis.spacexfan.R
import com.nexis.spacexfan.databinding.FragmentMainBinding
import com.nexis.spacexfan.util.Singleton
import com.nexis.spacexfan.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var v: View
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var transaction: FragmentTransaction
    private lateinit var mainViewModel: MainViewModel

    private lateinit var rocketsFragment: RocketsFragment
    private lateinit var favoritesFragment: FavoritesFragment
    private lateinit var upcomingFragment: UpcomingFragment

    private var userId: String? = null

    private fun init(){
        arguments?.let {
            userId = MainFragmentArgs.fromBundle(it).userId
            initFragments()

            mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            observeLiveData()

            if (userId == null)
                mainViewModel.checkUserLogin()
            else
                setFragment(rocketsFragment)

            Singleton.isLogout = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainBinding = FragmentMainBinding.inflate(inflater)
        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()

        mainBinding.mainFragmentNavView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.bottom_nav_menu_rockets -> {
                    mainBinding.mainFragmentTxtToolBarName.text = "SpaceX Rockets"
                    setFragment(rocketsFragment)
                    true
                }

                R.id.bottom_nav_menu_favorites -> {
                    mainBinding.mainFragmentTxtToolBarName.text = "Favorite Rockets"
                    setFragment(favoritesFragment)
                    true
                }

                R.id.bottom_nav_menu_upcoming -> {
                    mainBinding.mainFragmentTxtToolBarName.text = "Upcoming Launchers"
                    setFragment(upcomingFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun observeLiveData(){
        mainViewModel.loginedUserId.observe(viewLifecycleOwner, Observer {
            if (it != null){
                userId = it
                initFragments()
            }

            mainBinding.mainFragmentNavView.menu.findItem(R.id.bottom_nav_menu_rockets).setChecked(true)
            setFragment(rocketsFragment)
        })
    }

    private fun initFragments(){
        rocketsFragment = RocketsFragment(userId)
        favoritesFragment = FavoritesFragment(userId)
        upcomingFragment = UpcomingFragment()
    }

    private fun setFragment(fragment: Fragment){
        transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_frameLayout, fragment)
        transaction.commit()
    }
}