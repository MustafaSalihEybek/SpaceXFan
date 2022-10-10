package com.nexis.spacexfan.model.Rocket

import com.nexis.spacexfan.model.Rocket.Engines.Engines
import com.nexis.spacexfan.model.Rocket.FirstStage.FirstStage
import com.nexis.spacexfan.model.Rocket.SecondStage.SecondStage

data class Rocket(
    val height: Height,
    val diameter: Diameter,
    val mass: Mass,
    val first_stage: FirstStage,
    val second_stage: SecondStage,
    val engines: Engines,
    val landing_legs: LandingLegs,
    val payload_weights: PayloadWeights,
    val flickr_images: ArrayList<String>,
    val name: String,
    val type: String,
    val active: Boolean,
    val stages: Int,
    val boosters: Int,
    val cost_per_launch: Int,
    val success_rate_pct: Int,
    val first_flight: String,
    val country: String,
    val company: String,
    val wikipedia: String,
    val description: String,
    val id: String
)
