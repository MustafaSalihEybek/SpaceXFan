package com.nexis.spacexfan.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Upcoming(
    val name: String,
    val date_local: String,
    val id: String
) : Parcelable
