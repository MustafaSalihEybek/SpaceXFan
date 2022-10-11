package com.nexis.spacexfan.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.nexis.spacexfan.model.Favorite
import com.nexis.spacexfan.util.AppUtil
import com.nexis.spacexfan.util.FirebaseUtil
import com.nexis.spacexfan.viewmodel.base.BaseViewModel

class RocketDetailViewModel(application: Application) : BaseViewModel(application) {
    val isFavorite = MutableLiveData<Boolean>()

    fun checkFavorite(userId: String, rocketId: String){
        FirebaseUtil.checkFavorite(userId, rocketId, checkFavoriteListener = {isFavorite, onError ->
            errorMessage.value = onError
            this.isFavorite.value = isFavorite
        })
    }

    fun addFavorite(userId: String, rocketId: String){
        AppUtil.mFavorite = Favorite(rocketId)

        FirebaseUtil.addFavorite(userId, AppUtil.mFavorite, addFavoriteOnComplete = {onMessage ->
            successMessage.value = onMessage
        })
    }

    fun removeFavorite(userId: String, rocketId: String){
        FirebaseUtil.removeFavorite(userId, rocketId, removeFavoriteOnComplete = {onMessage ->
            successMessage.value = onMessage
        })
    }
}