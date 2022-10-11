package com.nexis.spacexfan.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.nexis.spacexfan.R
import com.nexis.spacexfan.adapter.RocketsAdapter
import com.nexis.spacexfan.adapter.decoration.LinearManagerDecoration
import com.nexis.spacexfan.databinding.FragmentFavoritesBinding
import com.nexis.spacexfan.model.Favorite
import com.nexis.spacexfan.model.Rocket
import com.nexis.spacexfan.util.Singleton
import com.nexis.spacexfan.util.show
import com.nexis.spacexfan.viewmodel.FavoritesViewModel

class FavoritesFragment(val userId: String?) : Fragment(), View.OnClickListener {
    private lateinit var v: View
    private lateinit var favoritesBinding: FragmentFavoritesBinding
    private lateinit var navDirections: NavDirections
    private lateinit var favoritesViewModel: FavoritesViewModel

    private lateinit var rocketsAdapter: RocketsAdapter
    private lateinit var favoriteList: ArrayList<Favorite>
    private lateinit var rocketList: ArrayList<Rocket>
    private var fIn: Int = 0

    private fun init(){
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        observeLiveData()

        if (userId != null){
            favoritesBinding.favoritesFragmentBtnSignOut.visibility = View.VISIBLE
            favoritesBinding.favoritesFragmentLinearSign.visibility = View.GONE
            favoritesBinding.favoritesFragmentRecyclerView.visibility = View.VISIBLE

            favoritesBinding.favoritesFragmentRecyclerView.setHasFixedSize(true)
            favoritesBinding.favoritesFragmentRecyclerView.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
            rocketsAdapter = RocketsAdapter(arrayListOf(), v, userId)
            favoritesBinding.favoritesFragmentRecyclerView.adapter = rocketsAdapter

            rocketList = ArrayList()
            fIn = 0
            favoritesViewModel.getFavorites(userId)
        } else {
            favoritesBinding.favoritesFragmentLinearSign.visibility = View.VISIBLE
            favoritesBinding.favoritesFragmentBtnSignOut.visibility = View.GONE
            favoritesBinding.favoritesFragmentRecyclerView.visibility = View.GONE
        }

        favoritesBinding.favoritesFragmentBtnSignOut.setOnClickListener(this)
        favoritesBinding.favoritesFragmentEditPassword.setOnClickListener(this)
        favoritesBinding.favoritesFragmentEditEmail.setOnClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoritesBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return favoritesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()
    }

    private fun observeLiveData(){
        favoritesViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                println(it)
            }
        })

        favoritesViewModel.favoriteList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                favoriteList = it

                if (fIn > (it.size - 1)){
                    fIn = 0
                    rocketList = ArrayList()
                }

                favoritesViewModel.getRocket(favoriteList.get(fIn).rocketId)
            } else
                rocketsAdapter.loadData(arrayListOf())
        })

        favoritesViewModel.rocketList.observe(viewLifecycleOwner, Observer {
            it?.let {
                rocketList.add(getRocketBySearch(favoriteList.get(fIn).rocketId, it))

                if (fIn < (favoriteList.size - 1)){
                    fIn++
                    favoritesViewModel.getRocket(favoriteList.get(fIn).rocketId)
                } else {
                    if (favoritesBinding.favoritesFragmentRecyclerView.itemDecorationCount > 0)
                        favoritesBinding.favoritesFragmentRecyclerView.removeItemDecorationAt(0)

                    favoritesBinding.favoritesFragmentRecyclerView.addItemDecoration(LinearManagerDecoration(Singleton.V_SIZE, rocketList.size))
                    rocketsAdapter.loadData(rocketList)
                }
            }
        })
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when (it.id){
                R.id.favorites_fragment_editEmail -> goToSignPage()
                R.id.favorites_fragment_editPassword -> goToSignPage()
                R.id.favorites_fragment_btnSignOut -> signOut()
            }
        }
    }

    private fun signOut(){
        "message".show(v, "Sign out...")
        Singleton.isLogout = true
        FirebaseAuth.getInstance().signOut()

        Handler(Looper.myLooper()!!).postDelayed({
            goToSignPage()
        }, 1000)
    }

    private fun goToSignPage(){
        navDirections = MainFragmentDirections.actionMainFragmentToSignInAndSignUpFragment()
        Navigation.findNavController(v).navigate(navDirections)
    }

    private fun getRocketBySearch(rocketId: String, rocketList: ArrayList<Rocket>) : Rocket {
        var rocketData: Rocket = rocketList[0]

        for (r in rocketList.indices){
            rocketList.get(r).id?.let {
                if (it == rocketId)
                    return rocketList.get(r)
            }
        }

        return rocketData
    }
}