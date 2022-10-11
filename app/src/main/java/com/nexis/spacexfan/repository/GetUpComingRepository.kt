package com.nexis.spacexfan.repository

import com.nexis.spacexfan.model.Upcoming
import com.nexis.spacexfan.util.AppUtil
import io.reactivex.Single

class GetUpComingRepository {
    fun getUpComing() : Single<ArrayList<Upcoming>> {
        return AppUtil.getAppAPI().getUpComing()
    }
}