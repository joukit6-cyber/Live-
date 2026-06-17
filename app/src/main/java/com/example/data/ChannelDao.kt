package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {
    @Query("SELECT * FROM favorite_channels")
    fun getAllFavorites(): Flow<List<FavoriteChannel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(channel: FavoriteChannel)

    @Query("DELETE FROM favorite_channels WHERE url = :url")
    suspend fun removeFavorite(url: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_channels WHERE url = :url LIMIT 1)")
    fun isFavorite(url: String): Flow<Boolean>
}
