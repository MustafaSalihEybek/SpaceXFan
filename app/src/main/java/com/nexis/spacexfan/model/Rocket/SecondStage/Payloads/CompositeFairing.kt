package com.nexis.spacexfan.model.Rocket.SecondStage.Payloads

import com.nexis.spacexfan.model.Rocket.Diameter
import com.nexis.spacexfan.model.Rocket.Height

data class CompositeFairing(
    val height: Height,
    val diameter: Diameter
)