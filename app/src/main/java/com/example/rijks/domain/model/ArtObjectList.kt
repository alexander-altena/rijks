package com.example.rijks.domain.model

sealed class ArtObjectList{

    data class ArtObjectItem(val art: ArtObject) : ArtObjectList()
    data class ArtObjectHeader(val header: String): ArtObjectList()

}
