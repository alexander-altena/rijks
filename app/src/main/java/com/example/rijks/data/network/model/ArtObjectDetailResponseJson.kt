package com.example.rijks.data.network.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ArtObjectDetailResponseJson(
    val artObject: ArtObjectDetailJson
)
