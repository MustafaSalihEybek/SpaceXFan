package com.nexis.spacexfan.model.RocketModels.Engines

import com.nexis.spacexfan.model.RocketModels.FirstStage.ThrustSeaLevel
import com.nexis.spacexfan.model.RocketModels.FirstStage.ThrustVacuum

data class Engines (
    val isp: Isp,
    val thrust_sea_level: ThrustSeaLevel,
    val thrust_vacuum: ThrustVacuum,
    val number: Int?,
    val type: String?,
    val version: String?,
    val layout: String?,
    val engine_loss_max: Int?,
    val propellant_1: String?,
    val propellant_2: String?,
    val thrust_to_weight: Double?
)