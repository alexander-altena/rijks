package com.example.rijks.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtObjectDetailLabelJson(
    val title: String,
    val makerLine: String?,
    val description: String,
)
