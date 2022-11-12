package com.scrip0.umassclasses.api.UMassAPI.entities

import com.google.gson.annotations.SerializedName

data class Units(
    @SerializedName("base")
    val base: Double = -1.0,
    @SerializedName("max")
    val max: Any = "",
    @SerializedName("min")
    val min: Any = ""
)