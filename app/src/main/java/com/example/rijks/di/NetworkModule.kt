package com.example.rijks.di

import android.content.Context
import androidx.room.Room
import com.example.rijks.data.network.service.RijksRetrofitService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {


    companion object{

        @Singleton
        @Provides
        fun provideRijksstudioRetrofitService(
            // Potential dependencies of this type
        ): RijksRetrofitService {
            return Retrofit.Builder()
                .baseUrl("https://www.rijksmuseum.nl/api/")
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(RijksRetrofitService::class.java)
        }

    }


}