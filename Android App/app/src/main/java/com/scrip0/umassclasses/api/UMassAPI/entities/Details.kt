package com.scrip0.umassclasses.api.UMassAPI.entities

import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("academic_group")
    val academic_group: String = "",
    @SerializedName("academic_organization")
    val academic_organization: String = "",
    @SerializedName("campus")
    val campus: String = "",
    @SerializedName("career")
    val career: String = "",
    @SerializedName("course_components")
    val course_components: List<String> = emptyList(),
    @SerializedName("grading_basis")
    val grading_basis: String = "",
    @SerializedName("units")
    val units: Units = Units()
)