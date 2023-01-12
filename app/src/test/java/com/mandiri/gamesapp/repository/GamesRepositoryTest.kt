package com.mandiri.gamesapp.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandiri.gamesapp.TestConstants
import com.mandiri.gamesapp.data.base.response.ApiResponse
import com.mandiri.gamesapp.data.games.GamesRepository
import com.mandiri.gamesapp.data.games.remote.IGamesRemoteSource
import com.mandiri.gamesapp.domain.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class GamesRepositoryTest {
    private lateinit var remoteSource: IGamesRemoteSource
    private lateinit var factory: GamesRepository

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        remoteSource = Mockito.mock(IGamesRemoteSource::class.java)
        factory = GamesRepository(remoteSource)
    }

    @Test
    fun test_GamesList_Return_Success() {
        runTest {
            // Given
            Mockito.`when`(remoteSource.getGamesList()).thenReturn(flow {
                emit(
                    ApiResponse.Success(
                        TestConstants.gamesResponse
                    )
                )
            })
            // When
            factory.getGamesList().collectLatest {
                if (it is Resource.Success) {
                    // Then
                    Mockito.verify(remoteSource, Mockito.atLeastOnce()).getGamesList()
                    Assert.assertEquals(
                        TestConstants.ONE,
                        it.data?.count
                    )
                }
            }
        }
    }

    @Test
    fun test_GamesList_Return_Error() {
        runTest {
            // Given
            Mockito.`when`(remoteSource.getGamesList()).thenReturn(flow {
                emit(ApiResponse.Error(TestConstants.THROWABLE))
            })
            // When
            factory.getGamesList().collectLatest {
                if (it is Resource.Error) {
                    // Then
                    Mockito.verify(remoteSource, Mockito.atLeastOnce()).getGamesList()
                    Assert.assertEquals(
                        TestConstants.ERROR_MESSAGE,
                        it.message
                    )
                }
            }
        }
    }

    @Test
    fun test_GamesPaging_Return_Success() {
        runTest {
            // Given
            Mockito.`when`(remoteSource.getGamesWithPaging(TestConstants.ONE)).thenReturn(flow {
                emit(
                    ApiResponse.Success(
                        TestConstants.gamesResponse
                    )
                )
            })
            // When
            factory.getGamesWithPaging(TestConstants.ONE).collectLatest {
                if (it is Resource.Success) {
                    // Then
                    Mockito.verify(remoteSource, Mockito.atLeastOnce())
                        .getGamesWithPaging(TestConstants.ONE)
                    Assert.assertEquals(
                        TestConstants.ONE,
                        it.data?.count
                    )
                }
            }
        }
    }

    @Test
    fun test_GamesPaging_Return_Error() {
        runTest {
            // Given
            Mockito.`when`(remoteSource.getGamesWithPaging(TestConstants.ONE)).thenReturn(flow {
                emit(ApiResponse.Error(TestConstants.THROWABLE))
            })
            // When
            factory.getGamesWithPaging(TestConstants.ONE).collectLatest {
                if (it is Resource.Error) {
                    // Then
                    Mockito.verify(remoteSource, Mockito.atLeastOnce())
                        .getGamesWithPaging(TestConstants.ONE)
                    Assert.assertEquals(
                        TestConstants.ERROR_MESSAGE,
                        it.message
                    )
                }
            }
        }
    }

    @Test
    fun test_GamesSearch_Return_Success() {
        runTest {
            // Given
            Mockito.`when`(remoteSource.getSearchGames(TestConstants.ONE, TestConstants.NAME)).thenReturn(flow {
                emit(
                    ApiResponse.Success(
                        TestConstants.gamesResponse
                    )
                )
            })
            // When
            factory.getSearchGames(TestConstants.ONE, TestConstants.NAME).collectLatest {
                if (it is Resource.Success) {
                    // Then
                    Mockito.verify(remoteSource, Mockito.atLeastOnce())
                        .getSearchGames(TestConstants.ONE, TestConstants.NAME)
                    Assert.assertEquals(
                        TestConstants.TRUE,
                        it.data?.results?.first()?.name?.contains(TestConstants.NAME)
                    )
                }
            }
        }
    }

    @Test
    fun test_GamesSearch_Return_Error() {
        runTest {
            // Given
            Mockito.`when`(remoteSource.getSearchGames(TestConstants.ONE, TestConstants.NAME)).thenReturn(flow {
                emit(ApiResponse.Error(TestConstants.THROWABLE))
            })
            // When
            factory.getSearchGames(TestConstants.ONE, TestConstants.NAME).collectLatest {
                if (it is Resource.Error) {
                    // Then
                    Mockito.verify(remoteSource, Mockito.atLeastOnce())
                        .getSearchGames(TestConstants.ONE, TestConstants.NAME)
                    Assert.assertEquals(
                        TestConstants.ERROR_MESSAGE,
                        it.message
                    )
                }
            }
        }
    }

    @Test
    fun test_GamesDetail_Return_Success() {
        runTest {
            // Given
            Mockito.`when`(remoteSource.getGamesDetail(TestConstants.ONE)).thenReturn(flow {
                emit(
                    ApiResponse.Success(
                        TestConstants.gamesDetailResponse
                    )
                )
            })
            // When
            factory.getGamesDetail(TestConstants.ONE).collectLatest {
                if (it is Resource.Success) {
                    // Then
                    Mockito.verify(remoteSource, Mockito.atLeastOnce())
                        .getGamesDetail(TestConstants.ONE)
                    Assert.assertEquals(
                        TestConstants.NAME,
                        it.data?.name
                    )
                }
            }
        }
    }

    @Test
    fun test_GamesDetail_Return_Error() {
        runTest {
            // Given
            Mockito.`when`(remoteSource.getGamesDetail(TestConstants.ONE)).thenReturn(flow {
                emit(ApiResponse.Error(TestConstants.THROWABLE))
            })
            // When
            factory.getGamesDetail(TestConstants.ONE).collectLatest {
                if (it is Resource.Error) {
                    // Then
                    Mockito.verify(remoteSource, Mockito.atLeastOnce())
                        .getGamesDetail(TestConstants.ONE)
                    Assert.assertEquals(
                        TestConstants.ERROR_MESSAGE,
                        it.message
                    )
                }
            }
        }
    }
}