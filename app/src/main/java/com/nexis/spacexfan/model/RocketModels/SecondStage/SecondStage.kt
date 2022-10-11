package com.nexis.spacexfan.model.RocketModels.SecondStage

import com.nexis.spacexfan.model.RocketModels.SecondStage.Payloads.Payloads

data class SecondStage(
    val thrust: Thrust,
    val payloads: Payloads,
    val reusable: Boolean?,
    val engines: Int?,
    val fuel_amount_tons: Double?,
    val burn_time_sec: Int?
)
