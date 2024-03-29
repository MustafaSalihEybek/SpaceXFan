package com.nexis.spacexfan.util

import com.nexis.spacexfan.api.AppAPI
import com.nexis.spacexfan.model.Favorite
import com.nexis.spacexfan.model.User
import com.nexis.spacexfan.repository.GetRocketRepository
import com.nexis.spacexfan.repository.GetRocketsRepository
import com.nexis.spacexfan.repository.GetUpComingRepository
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppUtil {
    lateinit var disposable: CompositeDisposable
    lateinit var mUser: User
    lateinit var mFavorite: Favorite

    lateinit var getRocketsRepository: GetRocketsRepository
    lateinit var getRocketRepository: GetRocketRepository
    lateinit var getUpComingRepository: GetUpComingRepository

    fun getAppAPI() : AppAPI {
        return Retrofit.Builder()
            .baseUrl(Singleton.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AppAPI::class.java)
    }
}