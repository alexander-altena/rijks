package com.example.rijks.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtWebImageJson (
    val width : Int,
    val height : Int,
    val url : String
)