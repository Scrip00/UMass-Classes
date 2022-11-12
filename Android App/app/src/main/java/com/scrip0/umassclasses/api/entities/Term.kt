package com.scrip0.umassclasses.api.entities

data class Term(
    val id: String,
    val ordinal: Int,
    val season: String,
    val url: String,
    val year: Int
)