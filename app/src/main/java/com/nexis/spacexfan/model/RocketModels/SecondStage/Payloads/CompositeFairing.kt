package com.nexis.spacexfan.model.RocketModels.SecondStage.Payloads

import com.nexis.spacexfan.model.RocketModels.Diameter
import com.nexis.spacexfan.model.RocketModels.Height

data class CompositeFairing(
    val height: Height?,
    val diameter: Diameter?
)