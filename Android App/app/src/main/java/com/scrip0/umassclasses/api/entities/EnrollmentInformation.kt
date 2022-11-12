package com.scrip0.umassclasses.api.entities

data class EnrollmentInformation(
    val add_consent: Any,
    val course_attribute: List<String>,
    val enrollment_requirement: String
)