package com.nexis.spacexfan.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.nexis.spacexfan.model.Rocket.Rocket
import com.nexis.spacexfan.repository.GetRocketsRepository
import com.nexis.spacexfan.util.AppUtil
import com.nexis.spacexfan.viewmodel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RocketsViewModel(application: Application) : BaseViewModel(application) {
    val rocketList = MutableLiveData<ArrayList<Rocket>>()

    fun getRockets(){
        AppUtil.getRocketsRepository = GetRocketsRepository()
        AppUtil.disposable = CompositeDisposable()

        AppUtil.disposable.add(
            AppUtil.getRocketsRepository.getRockets()
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