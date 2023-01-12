package com.mandiri.gamesapp.remote

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.mandiri.gamesapp.TestConstants
import com.mandiri.gamesapp.TestConstants.SLASH
import com.mandiri.gamesapp.data.base.response.ApiResponse
import com.mandiri.gamesapp.data.games.remote.GamesRemoteSource
import com.mandiri.gamesapp.data.games.remote.GamesService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
class GamesRemoteSourceTest {
    private lateinit var service: GamesService
    private lateinit var mockWebServer: MockWebServer
    private lateinit var remoteSource: GamesRemoteSource

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(SLASH).toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GamesService::class.java)
        remoteSource = GamesRemoteSource(service)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_GamesList_Return_Success() {
        runTest {
            val expectedResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(Gson().toJson(TestConstants.gamesResponse))
            mockWebServer.enqueue(expectedResponse)

            remoteSource.getGamesList().collect {
                if (it is ApiResponse.Success) {
                    Assert.assertEquals(TestConstants.gamesResponse, it.data)
                }
            }
        }
    }

    @Test
    fun test_GamesList_Return_Error() {
        runTest {
            val expectedResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            mockWebServer.enqueue(expectedResponse)
            remoteSource.getGamesList().collect {
                if (it is ApiResponse.Error) {
                    Assert.assertEquals(TestConstants.DEFAULT_ERROR_MESSAGE, it.throwable.message)
                }
            }
        }
    }

    @Test
    fun test_GamesPaging_Return_Success() {
        runTest {
            val expectedResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(Gson().toJson(TestConstants.gamesResponse))
            mockWebServer.enqueue(expectedResponse)

            remoteSource.getGamesWithPaging(TestConstants.ONE).collect {
                if (it is ApiResponse.Success) {
                    Assert.assertEquals(TestConstants.gamesResponse, it.data)
                }
            }
        }
    }

    @Test
    fun test_GamesPaging_Return_Error() {
        runTest {
            val expectedResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            mockWebServer.enqueue(expectedResponse)
            remoteSource.getGamesWithPaging(TestConstants.ONE).collect {
                if (it is ApiResponse.Error) {
                    Assert.assertEquals(TestConstants.DEFAULT_ERROR_MESSAGE, it.throwable.message)
                }
            }
        }
    }

    @Test
    fun test_GamesSearch_Return_Success() {
        runTest {
            val expectedResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(Gson().toJson(TestConstants.gamesResponse))
            mockWebServer.enqueue(expectedResponse)

            remoteSource.getSearchGames(TestConstants.ONE, TestConstants.NAME).collect {
                if (it is ApiResponse.Success) {
                    Assert.assertEquals(TestConstants.gamesResponse, it.data)
                }
            }
        }
    }

    @Test
    fun test_GamesSearch_Return_Error() {
        runTest {
            val expectedResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            mockWebServer.enqueue(expectedResponse)
            remoteSource.getSearchGames(TestConstants.ONE, TestConstants.NAME).collect {
                if (it is ApiResponse.Error) {
                    Assert.assertEquals(TestConstants.DEFAULT_ERROR_MESSAGE, it.throwable.message)
                }
            }
        }
    }

    @Test
    fun test_GamesDetail_Return_Success() {
        runTest {
            val expectedResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(Gson().toJson(TestConstants.gamesDetailResponse))
            mockWebServer.enqueue(expectedResponse)

            remoteSource.getGamesDetail(TestConstants.ONE).collect {
                if (it is ApiResponse.Success) {
                    Assert.assertEquals(TestConstants.gamesDetailResponse, it.data)
                }
            }
        }
    }

    @Test
    fun test_GamesDetail_Return_Error() {
        runTest {
            val expectedResponse = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            mockWebServer.enqueue(expectedResponse)
            remoteSource.getGamesDetail(TestConstants.ONE).collect {
                if (it is ApiResponse.Error) {
                    Assert.assertEquals(TestConstants.DEFAULT_ERROR_MESSAGE, it.throwable.message)
                }
            }
        }
    }
}