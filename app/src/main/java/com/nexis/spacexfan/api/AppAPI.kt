package com.nexis.spacexfan.api

import com.nexis.spacexfan.model.Rocket
import io.reactivex.Single
import retrofit2.http.GET

interface AppAPI {
    @GET("v4/rockets")
    fun getRockets() : Single<ArrayList<Rocket>>
}