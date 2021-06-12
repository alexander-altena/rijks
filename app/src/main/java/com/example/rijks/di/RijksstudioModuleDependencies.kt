package com.example.rijks.di

import com.example.rijks.data.database.ArtObjectDao
import com.example.rijks.data.database.RemoteKeysDao
import com.example.rijks.data.database.RijksDatabase
import com.example.rijks.data.network.service.RijksRetrofitService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface RijksstudioModuleDependencies {

    fun rijksstudioDatabase() : RijksDatabase

    fun artObjectsDao() : ArtObjectDao

    fun remoteKeysDao() : RemoteKeysDao

    fun rijksstudioRetrofitService() : RijksRetrofitService

}