package com.scrip0.umassclasses.api.UMassAPI.entities

import com.google.gson.annotations.SerializedName

data class EnrollmentInformation(
    @SerializedName("add_consent")
    val add_consent: Any = "",
    @SerializedName("course_attribute")
    val course_attribute: List<String> = emptyList(),
    @SerializedName("enrollment_requirement")
    val enrollment_requirement: String = ""
)