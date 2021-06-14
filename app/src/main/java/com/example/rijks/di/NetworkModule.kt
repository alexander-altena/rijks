package com.example.rijks.di

import com.example.rijks.data.network.service.RijksRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRijksstudioRetrofitService(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): RijksRetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://www.rijksmuseum.nl/api/")
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create(RijksRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideMoshiConverter(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

}