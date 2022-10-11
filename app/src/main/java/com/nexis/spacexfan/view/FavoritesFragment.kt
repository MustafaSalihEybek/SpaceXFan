package com.nexis.spacexfan.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.nexis.spacexfan.R
import com.nexis.spacexfan.databinding.FragmentFavoritesBinding
import com.nexis.spacexfan.util.show

class FavoritesFragment(val userId: String?) : Fragment(), View.OnClickListener {
    private lateinit var v: View
    private lateinit var favoritesBinding: FragmentFavoritesBinding
    private lateinit var navDirections: NavDirections

    private fun init(){
        if (userId != null){
            favoritesBinding.favoritesFragmentBtnSignOut.visibility = View.VISIBLE
            favoritesBinding.favoritesFragmentLinearSign.visibility = View.GONE
        } else {
            favoritesBinding.favoritesFragmentLinearSign.visibility = View.VISIBLE
            favoritesBinding.favoritesFragmentBtnSignOut.visibility = View.GONE
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
        FirebaseAuth.getInstance().signOut()

        Handler(Looper.myLooper()!!).postDelayed({
            goToSignPage()
        }, 1000)
    }

    private fun goToSignPage(){
        navDirections = MainFragmentDirections.actionMainFragmentToSignInAndSignUpFragment()
        Navigation.findNavController(v).navigate(navDirections)
    }
}