package com.nexis.spacexfan.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.nexis.spacexfan.model.Favorite
import com.nexis.spacexfan.model.Rocket
import com.nexis.spacexfan.repository.GetRocketRepository
import com.nexis.spacexfan.util.AppUtil
import com.nexis.spacexfan.util.FirebaseUtil
import com.nexis.spacexfan.viewmodel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FavoritesViewModel(application: Application) : BaseViewModel(application) {
    private lateinit var favorites: ArrayList<Favorite>

    val favoriteList = MutableLiveData<ArrayList<Favorite>>()
    val rocketList = MutableLiveData<ArrayList<Rocket>>()

    fun getFavorites(userId: String){
        FirebaseUtil.mQuery = FirebaseUtil.mFireStore.collection("Users")
            .document(userId).collection("Favorites")

        FirebaseUtil.mQuery.addSnapshotListener { value, error ->
            if (error != null){
                errorMessage.value = error.message
                return@addSnapshotListener
            }

            favorites = ArrayList()

            if (value != null){
                if (value.documents.size > 0){
                    for (s in value.documents.indices){
                        if (value.documents.get(s).exists()){
                            AppUtil.mFavorite = value.documents.get(s).toObject(Favorite::class.java)!!
                            favorites.add(AppUtil.mFavorite)

                            if (s == (value.documents.size - 1))
                                favoriteList.value = favorites
                        } else {
                            if (s == (value.documents.size - 1))
                                favoriteList.value = favorites
                        }
                    }
                } else
                    favoriteList.value = favorites
            } else
                favoriteList.value = favorites
        }
    }

    fun getRocket(rocketId: String){
        AppUtil.disposable = CompositeDisposable()
        AppUtil.getRocketRepository = GetRocketRepository()

        AppUtil.disposable.add(
            AppUtil.getRocketRepository.getRocket(rocketId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<Rocket>>(){
                    override fun onSuccess(t: ArrayList<Rocket>) {
                        rocketList.value = t
                    }

                    override fun onError(e: Throwable) {
                        errorMessage.value = e.localizedMessage
                    }
                })
        )
    }
}