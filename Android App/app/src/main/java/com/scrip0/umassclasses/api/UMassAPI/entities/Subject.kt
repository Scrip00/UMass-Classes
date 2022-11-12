package com.scrip0.umassclasses.api.UMassAPI.entities

import com.google.gson.annotations.SerializedName

data class Subject(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = ""
)