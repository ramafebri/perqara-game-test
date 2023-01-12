package com.mandiri.gamesapp.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandiri.gamesapp.TestConstants
import com.mandiri.gamesapp.TestConstants.ONE
import com.mandiri.gamesapp.data.favorite.FavoriteRepository
import com.mandiri.gamesapp.data.favorite.local.GamesDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class FavoriteRepositoryTest {
    private lateinit var dao: GamesDao
    private lateinit var factory: FavoriteRepository

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        dao = Mockito.mock(GamesDao::class.java)
        factory = FavoriteRepository(dao)
    }

    @Test
    fun add_GamesToFavorite() {
        runTest {
            factory.addGameToFavorite(TestConstants.gamesDetailModel)
            Mockito.verify(dao, Mockito.atLeastOnce()).insert(TestConstants.gamesEntity)
        }
    }

    @Test
    fun del_GamesFromFavorite() {
        runTest {
            factory.deleteGameFromFavorite(TestConstants.gameItemModel)
            Mockito.verify(dao, Mockito.atLeastOnce()).delete(TestConstants.gamesEntity)
        }
    }

    @Test
    fun get_GamesById() {
        runTest {
            // Given
            Mockito.`when`(dao.getGameById(ONE)).thenReturn(flow {
                emit(
                    TestConstants.gamesEntity
                )
            })
            // When
            factory.getGameById(ONE).collect {
                // Then
                Mockito.verify(dao, Mockito.atLeastOnce()).getGameById(ONE)
                Assert.assertEquals(
                    ONE,
                    it?.gameId
                )
            }
        }
    }

    @Test
    fun get_GamesById_Null() {
        runTest {
            // Given
            Mockito.`when`(dao.getGameById(ONE)).thenReturn(flow {
                emit(null)
            })
            // When
            factory.getGameById(ONE).collect {
                // Then
                Mockito.verify(dao, Mockito.atLeastOnce()).getGameById(ONE)
                Assert.assertEquals(
                    null,
                    it
                )
            }
        }
    }
}