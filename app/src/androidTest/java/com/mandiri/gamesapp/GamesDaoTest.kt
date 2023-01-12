package com.mandiri.gamesapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mandiri.gamesapp.data.favorite.local.GamesDao
import com.mandiri.gamesapp.data.favorite.local.GamesDatabase
import com.mandiri.gamesapp.data.favorite.local.GamesEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
@SmallTest
class GamesDaoTest {
    private lateinit var database: GamesDatabase
    private lateinit var dao: GamesDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GamesDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.gamesDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insert_returnsTrue() = runBlocking {
        for (i in 0..3) {
            dao.insert(
                GamesEntity(
                    rating = 1.1f,
                    gameId = i,
                    released = "RELEASED",
                    backgroundImage = "BACKGROUND",
                    name = "NAME",
                    localGameId = 0
                )
            )
        }
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            dao.getGameById(3).collectLatest {
                assertThat(it?.gameId).isEqualTo(3)
                latch.countDown()
            }
        }
        withContext(Dispatchers.IO) {
            latch.await()
        }
        job.cancelAndJoin()
    }

    @Test
    fun delete_returnsTrue() = runBlocking {
        for (i in 0..3) {
            dao.insert(
                GamesEntity(
                    rating = 1.1f,
                    gameId = i,
                    released = "RELEASED",
                    backgroundImage = "BACKGROUND",
                    name = "NAME",
                    localGameId = 0
                )
            )
        }
        dao.delete(
            GamesEntity(
                rating = 1.1f,
                gameId = 0,
                released = "RELEASED",
                backgroundImage = "BACKGROUND",
                name = "NAME",
                localGameId = 1
            )
        )
        val item = dao.getGamesList(10, 0)
        assertThat(item).hasSize(3)
        assertThat(item.first().name).contains("NAME")
    }
}