package com.example.rijks.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtObjectJson(
    val id : String,
    val objectNumber : String,
    val title : String,
    val hasImage : Boolean,
    val principalOrFirstMaker : String?,
    val longTitle : String,
    val showImage : Boolean,
    val permitDownload : Boolean,
    val webImage : ArtWebImageJson,

)


