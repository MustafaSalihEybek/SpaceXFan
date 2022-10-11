package com.nexis.spacexfan.repository

import com.nexis.spacexfan.model.Rocket
import com.nexis.spacexfan.util.AppUtil
import io.reactivex.Single

class GetRocketRepository {
    fun getRocket(rocketId: String) : Single<ArrayList<Rocket>> {
        return AppUtil.getAppAPI().getRocket(rocketId)
    }
}