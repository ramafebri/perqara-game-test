package com.mandiri.gamesapp.pagingSource

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.mandiri.gamesapp.TestConstants
import com.mandiri.gamesapp.TestConstants.ONE
import com.mandiri.gamesapp.TestConstants.TWO
import com.mandiri.gamesapp.TestConstants.ZERO
import com.mandiri.gamesapp.data.favorite.local.GamesDao
import com.mandiri.gamesapp.domain.favorite.repository.GamesPagingSource
import com.mandiri.gamesapp.domain.games.model.GameItemModel
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class GamesPagingSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var gamesDao: GamesDao
    private lateinit var pagingSource: GamesPagingSource

    @Before
    fun before() {
        mockkObject(GameItemModel.Companion)
        gamesDao = Mockito.mock(GamesDao::class.java)
        pagingSource = GamesPagingSource(gamesDao)
    }

    @Test
    fun `get games from local - success`() {
        runTest {
            // Given
            Mockito.`when`(gamesDao.getGamesList(ONE, ZERO)).thenReturn(
                TestConstants.gamesEntityList
            )
            every { GameItemModel.Companion.fromListEntity(TestConstants.gamesEntityList) } answers {
                TestConstants.gameItemModelList
            }
            // When
            val expectedResult = PagingSource.LoadResult.Page(
                data = GameItemModel.Companion.fromListEntity(TestConstants.gamesEntityList),
                prevKey = null,
                nextKey = ONE
            )
            // Then
            assertEquals(
                expectedResult, pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = ZERO,
                        loadSize = ONE,
                        placeholdersEnabled = false
                    )
                )
            )
            Mockito.verify(gamesDao, Mockito.atLeastOnce()).getGamesList(ONE, ZERO)
        }
    }

    @Test
    fun `get games from local - append`() = runTest {
        Mockito.`when`(gamesDao.getGamesList(ONE, ONE)).thenReturn(
            TestConstants.gamesEntityList
        )
        every { GameItemModel.Companion.fromListEntity(TestConstants.gamesEntityList) } answers {
            TestConstants.gameItemModelList
        }
        // When
        val expectedResult = PagingSource.LoadResult.Page(
            data = GameItemModel.Companion.fromListEntity(TestConstants.gamesEntityList),
            prevKey = ZERO,
            nextKey = TWO
        )
        // Then
        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Append(
                    key = ONE,
                    loadSize = ONE,
                    placeholdersEnabled = false
                )
            )
        )
        Mockito.verify(gamesDao, Mockito.atLeastOnce()).getGamesList(ONE, ONE)
    }

    @Test
    fun `get games from local - prepend`() = runTest {
        // Given
        Mockito.`when`(gamesDao.getGamesList(ONE, ZERO)).thenReturn(
            TestConstants.gamesEntityList
        )
        every { GameItemModel.Companion.fromListEntity(TestConstants.gamesEntityList) } answers {
            TestConstants.gameItemModelList
        }
        // When
        val expectedResult = PagingSource.LoadResult.Page(
            data = GameItemModel.Companion.fromListEntity(TestConstants.gamesEntityList),
            prevKey = null,
            nextKey = ONE
        )
        // Then
        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = ZERO,
                    loadSize = ONE,
                    placeholdersEnabled = false
                )
            )
        )
        Mockito.verify(gamesDao, Mockito.atLeastOnce()).getGamesList(ONE, ZERO)
    }
}