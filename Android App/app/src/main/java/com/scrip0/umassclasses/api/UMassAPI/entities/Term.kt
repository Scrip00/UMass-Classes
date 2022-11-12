package com.scrip0.umassclasses.api.UMassAPI.entities

import com.google.gson.annotations.SerializedName

data class Term(
    @SerializedName("id")
    val id: String,
    @SerializedName("ordinal")
    val ordinal: Int,
    @SerializedName("season")
    val season: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("year")
    val year: Int
)