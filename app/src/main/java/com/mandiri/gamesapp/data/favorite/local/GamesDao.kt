package com.mandiri.gamesapp.data.favorite.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(gamesEntity: GamesEntity)

    @Delete
    suspend fun delete(gamesEntity: GamesEntity)

    @Query("SELECT * from gamesEntity ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getGamesList(limit: Int, offset: Int): List<GamesEntity>

    @Query("SELECT * from gamesEntity WHERE gamesEntity.gameId == :gameId")
    fun getGameById(gameId: Int): Flow<GamesEntity?>
}