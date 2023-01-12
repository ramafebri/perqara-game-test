package com.mandiri.gamesapp.di

import com.mandiri.gamesapp.data.favorite.FavoriteRepository
import com.mandiri.gamesapp.data.games.GamesRepository
import com.mandiri.gamesapp.domain.favorite.repository.IFavoriteRepository
import com.mandiri.gamesapp.domain.games.repository.IGamesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideGamesRepository(repository: GamesRepository): IGamesRepository

    @Binds
    abstract fun provideFavoriteRepository(repository: FavoriteRepository): IFavoriteRepository
}