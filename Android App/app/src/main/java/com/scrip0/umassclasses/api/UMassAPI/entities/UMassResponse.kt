package com.scrip0.umassclasses.api.UMassAPI.entities

data class UMassResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: MutableList<Result>
)