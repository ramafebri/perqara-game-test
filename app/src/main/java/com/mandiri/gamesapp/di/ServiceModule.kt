package com.mandiri.gamesapp.di

import com.mandiri.gamesapp.data.games.remote.GamesRemoteSource
import com.mandiri.gamesapp.data.games.remote.IGamesRemoteSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideGamesRemoteSource(remoteSource: GamesRemoteSource): IGamesRemoteSource
}