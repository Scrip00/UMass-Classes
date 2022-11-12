package com.scrip0.umassclasses.api.UMassAPI.entities

import com.google.gson.annotations.SerializedName

data class Offering(
    @SerializedName("id")
    val id: Int,
    @SerializedName("term")
    val term: Term,
    @SerializedName("url")
    val url: String
)