package com.sample.myplayer.di

import android.content.Context
import com.sample.myplayer.data.datasource.MusicDataSourceImpl
import com.sample.myplayer.data.repository.MusicRepositoryImpl
import com.sample.myplayer.data.service.MusicControllerImpl
import com.sample.myplayer.domain.datasource.MusicDataSource
import com.sample.myplayer.domain.repository.MusicRepository
import com.sample.myplayer.domain.service.MusicController
import com.sample.myplayer.util.JsonReaderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun provideMusicRepository(
        musicDataSource: MusicDataSource,
    ): MusicRepository =
        MusicRepositoryImpl(musicDataSource)

    @Singleton
    @Provides
    fun provideMusicDataSource(
        jsonReaderApi: JsonReaderApi
    ): MusicDataSource =
        MusicDataSourceImpl(jsonReaderApi)


    @Singleton
    @Provides
    fun provideMusicController(@ApplicationContext context: Context): MusicController =
        MusicControllerImpl(context)
}