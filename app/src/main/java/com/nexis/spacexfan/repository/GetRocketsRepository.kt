package com.nexis.spacexfan.repository

import com.nexis.spacexfan.model.Rocket.Rocket
import com.nexis.spacexfan.util.AppUtil
import io.reactivex.Single

class GetRocketsRepository {
    fun getRockets() : Single<ArrayList<Rocket>> {
        return AppUtil.getAppAPI().getRockets()
    }
}