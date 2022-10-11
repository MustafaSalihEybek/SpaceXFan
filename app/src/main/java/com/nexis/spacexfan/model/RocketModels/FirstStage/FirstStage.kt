package com.nexis.spacexfan.model.RocketModels.FirstStage

data class FirstStage(
    val thrust_sea_level: ThrustSeaLevel,
    val thrust_vacuum: ThrustVacuum,
    val reusable: Boolean?,
    val engines: Int?,
    val fuel_amount_tons: Double?,
    val burn_time_sec: Int?
)