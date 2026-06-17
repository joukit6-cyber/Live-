package com.example.data

import kotlinx.coroutines.flow.Flow

class ChannelRepository(private val channelDao: ChannelDao) {
    val favorites: Flow<List<FavoriteChannel>> = channelDao.getAllFavorites()

    suspend fun addFavorite(channel: FavoriteChannel) {
        channelDao.insertFavorite(channel)
    }

    suspend fun removeFavorite(url: String) {
        channelDao.removeFavorite(url)
    }

    fun isFavorite(url: String): Flow<Boolean> {
        return channelDao.isFavorite(url)
    }
}
