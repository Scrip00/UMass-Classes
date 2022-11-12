package com.scrip0.umassclasses.api.entities

data class Details(
    val academic_group: String,
    val academic_organization: String,
    val campus: String,
    val career: String,
    val course_components: List<String>,
    val grading_basis: String,
    val units: Units
)