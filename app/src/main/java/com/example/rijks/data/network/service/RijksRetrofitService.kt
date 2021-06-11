package com.example.rijks.data.network.service

import com.example.rijks.data.network.model.ArtObjectDetailResponseJson
import com.example.rijks.data.network.model.ArtObjectResponseJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RijksRetrofitService {

    @GET("{culture}/collection")
    suspend fun getAllArtObjects(@Path("culture") culture: String, @QueryMap options: HashMap<String, String>): ArtObjectResponseJson

    @GET("{culture}/collection/{objectNumber}")
    suspend fun getArtObjectDetails(@Path("culture") culture: String, @Path("objectNumber") objectNumber: String, @Query("key") key: String): ArtObjectDetailResponseJson

}