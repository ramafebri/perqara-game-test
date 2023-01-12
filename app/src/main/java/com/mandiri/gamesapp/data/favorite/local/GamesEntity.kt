package com.mandiri.gamesapp.data.favorite.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mandiri.gamesapp.domain.games.model.GameItemModel
import com.mandiri.gamesapp.domain.games.model.GamesDetailModel
import kotlinx.parcelize.Parcelize

@Entity(tableName = "gamesEntity")
@Parcelize
data class GamesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var localGameId: Int = 0,
    @ColumnInfo(name = "gameId")
    var gameId: Int? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "backgroundImage")
    var backgroundImage: String? = null,
    @ColumnInfo(name = "released")
    var released: String? = null,
    @ColumnInfo(name = "rating")
    var rating: Float? = null
) : Parcelable {
    companion object {
        fun fromDetail(model: GamesDetailModel): GamesEntity {
            return GamesEntity(
                gameId = model.gameId,
                name = model.name,
                backgroundImage = model.backgroundImage,
                released = model.released,
                rating = model.rating
            )
        }

        fun fromItem(model: GameItemModel): GamesEntity {
            return GamesEntity(
                gameId = model.gameId,
                name = model.name,
                backgroundImage = model.backgroundImage,
                released = model.released,
                rating = model.rating,
                localGameId = model.localGameId
            )
        }
    }
}