package com.nexis.spacexfan.viewmodel

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.nexis.spacexfan.util.FirebaseUtil
import com.nexis.spacexfan.viewmodel.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    fun checkUserLogin(){
        FirebaseUtil.fUser = FirebaseAuth.getInstance().currentUser

        if (FirebaseUtil.fUser != null){
            if (FirebaseUtil.fUser!!.isEmailVerified)
                loginedUserId.value = FirebaseUtil.fUser?.uid
            else
                loginedUserId.value = null
        } else
            loginedUserId.value = null
    }
}