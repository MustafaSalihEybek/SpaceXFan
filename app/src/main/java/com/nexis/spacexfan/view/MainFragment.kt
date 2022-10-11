package com.nexis.spacexfan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.nexis.spacexfan.R
import com.nexis.spacexfan.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var v: View
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var transaction: FragmentTransaction

    private lateinit var rocketsFragment: RocketsFragment
    private lateinit var favoritesFragment: FavoritesFragment
    private lateinit var upcomingFragment: UpcomingFragment

    private fun init(){
        rocketsFragment = RocketsFragment()
        favoritesFragment = FavoritesFragment()
        upcomingFragment = UpcomingFragment()

        setFragment(rocketsFragment)
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

    private fun setFragment(fragment: Fragment){
        transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_frameLayout, fragment)
        transaction.commit()
    }
}