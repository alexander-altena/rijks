package com.example.rijks.domain.model


data class ArtObject (
    val id : String,
    val objectNumber : String,
    val title : String,
    val principalOrFirstMaker : String?,
    val imageUrl : String,
    val imgRatio: Float
)