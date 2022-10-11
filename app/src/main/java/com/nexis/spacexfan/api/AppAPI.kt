package com.nexis.spacexfan.api

import com.nexis.spacexfan.model.Rocket
import com.nexis.spacexfan.model.Upcoming
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AppAPI {
    @GET("v4/rockets")
    fun getRockets() : Single<ArrayList<Rocket>>

    @GET("v4/rockets")
    fun getRocket(@Query("rocket_id") rocketId: String) : Single<ArrayList<Rocket>>

    @GET("v5/launches/upcoming")
    fun getUpComing() : Single<ArrayList<Upcoming>>
}