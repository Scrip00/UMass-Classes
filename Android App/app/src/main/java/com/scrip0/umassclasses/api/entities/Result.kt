package com.scrip0.umassclasses.api.entities

data class Result(
    val _updated_at: String,
    val description: String,
    val details: Details,
    val enrollment_information: EnrollmentInformation,
    val id: String,
    val number: String,
    val offerings: List<Offering>,
    val subject: Subject,
    val title: String,
    val url: String
)