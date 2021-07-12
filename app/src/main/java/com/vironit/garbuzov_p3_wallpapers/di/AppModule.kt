package com.vironit.garbuzov_p3_wallpapers.di

import android.app.Application
import androidx.room.Room
import com.vironit.garbuzov_p3_wallpapers.api.PhotosSearchApi
import com.vironit.garbuzov_p3_wallpapers.data.database.PhotosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(PhotosSearchApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): PhotosSearchApi =
        retrofit.create(PhotosSearchApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(application: Application): PhotosDatabase {
        return Room
            .databaseBuilder(application, PhotosDatabase::class.java, "favorite_photos.db")
            .build()
    }
}