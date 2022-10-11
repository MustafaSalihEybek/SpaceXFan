package com.nexis.spacexfan.viewmodel

import android.app.Application
import com.nexis.spacexfan.model.User
import com.nexis.spacexfan.util.AppUtil
import com.nexis.spacexfan.util.FirebaseUtil
import com.nexis.spacexfan.viewmodel.base.BaseViewModel

class SignViewModel(application: Application) : BaseViewModel(application) {
    private lateinit var userId: String

    fun signUpUser(userEmail: String, userPassword: String){
        FirebaseUtil.mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    FirebaseUtil.fUser = it.result.user

                    FirebaseUtil.fUser?.let {
                        userId = it.uid

                        it.sendEmailVerification()
                            .addOnCompleteListener {
                                if (it.isSuccessful){
                                    AppUtil.mUser = User(userId, userEmail)

                                    FirebaseUtil.mFireStore.collection("Users").document(userId)
                                        .set(AppUtil.mUser)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful)
                                                successMessage.value = "Successfully registered, please confirm the link sent to your email address"
                                            else
                                                errorMessage.value = it.exception?.message
                                        }
                                } else
                                    errorMessage.value = it.exception?.message
                            }
                    }
                } else
                    errorMessage.value = it.exception?.message
            }
    }

    fun signInUser(userEmail: String, userPassword: String){
        FirebaseUtil.mAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    FirebaseUtil.fUser = it.result.user

                    FirebaseUtil.fUser?.let {
                        if (it.isEmailVerified)
                            loginedUserId.value = it.uid
                        else
                            errorMessage.value = "Please confirm the link sent to your email address."
                    }
                } else
                    errorMessage.value = it.exception?.message
            }
    }
}