package com.scrip0.umassclasses.api.TextAPI.entities

data class TextResponse(
    val lang: String,
    val langConfidence: String,
    val similarity: String,
    val time: String,
    val timestamp: String
)