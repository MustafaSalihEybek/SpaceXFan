package com.nexis.spacexfan.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.nexis.spacexfan.model.Upcoming
import com.nexis.spacexfan.repository.GetUpComingRepository
import com.nexis.spacexfan.util.AppUtil
import com.nexis.spacexfan.viewmodel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UpComingViewModel(application: Application) : BaseViewModel(application) {
    val upcomingList = MutableLiveData<ArrayList<Upcoming>>()

    fun getUpComing(){
        AppUtil.disposable = CompositeDisposable()
        AppUtil.getUpComingRepository = GetUpComingRepository()

        AppUtil.disposable.add(
            AppUtil.getUpComingRepository.getUpComing()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<Upcoming>>(){
                    override fun onSuccess(t: ArrayList<Upcoming>) {
                        upcomingList.value = t
                    }

                    override fun onError(e: Throwable) {
                        errorMessage.value = e.localizedMessage
                    }
                })
        )
    }
}