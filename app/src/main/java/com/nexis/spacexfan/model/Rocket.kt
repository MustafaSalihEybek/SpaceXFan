package com.nexis.spacexfan.model

import android.os.Parcelable
import com.nexis.spacexfan.model.RocketModels.*
import com.nexis.spacexfan.model.RocketModels.Engines.Engines
import com.nexis.spacexfan.model.RocketModels.FirstStage.FirstStage
import com.nexis.spacexfan.model.RocketModels.SecondStage.SecondStage
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Rocket(
    val height: @RawValue Height,
    val diameter: @RawValue Diameter,
    val mass: @RawValue Mass,
    val first_stage: @RawValue FirstStage,
    val second_stage: @RawValue SecondStage,
    val engines: @RawValue Engines,
    val landing_legs: @RawValue LandingLegs,
    val payload_weights: @RawValue ArrayList<Weight>,
    val flickr_images: ArrayList<String>,
    val name: String?,
    val type: String?,
    val active: Boolean?,
    val stages: Int?,
    val boosters: Int?,
    val cost_per_launch: Int?,
    val success_rate_pct: Int?,
    val first_flight: String?,
    val country: String?,
    val company: String?,
    val wikipedia: String?,
    val description: String?,
    val id: String?
) : Parcelable
