package com.mandiri.gamesapp.di

import android.content.Context
import androidx.room.Room
import com.mandiri.gamesapp.data.favorite.local.GamesDao
import com.mandiri.gamesapp.data.favorite.local.GamesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): GamesDatabase {
        return Room.databaseBuilder(
            appContext,
            GamesDatabase::class.java,
            "GamesDatabase"
        ).build()
    }

    @Provides
    fun provideChannelDao(appDatabase: GamesDatabase): GamesDao {
        return appDatabase.gamesDao()
    }
}