package com.mandiri.gamesapp.di

import com.mandiri.gamesapp.domain.favorite.usecase.FavoriteUseCase
import com.mandiri.gamesapp.domain.favorite.usecase.IFavoriteUseCase
import com.mandiri.gamesapp.domain.games.usecase.GamesUseCase
import com.mandiri.gamesapp.domain.games.usecase.IGamesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideGamesUseCase(gamesUseCase: GamesUseCase): IGamesUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideFavoriteUseCase(favoriteUseCase: FavoriteUseCase): IFavoriteUseCase

}