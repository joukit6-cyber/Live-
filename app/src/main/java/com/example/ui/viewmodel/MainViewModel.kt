package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.ChannelRepository
import com.example.data.FavoriteChannel
import com.example.model.Channel
import com.example.util.M3uParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application, database: AppDatabase) : AndroidViewModel(application) {
    private val repository = ChannelRepository(database.channelDao())

    private val _allChannels = MutableStateFlow<List<Channel>>(emptyList())
    val allChannels: StateFlow<List<Channel>> = _allChannels.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val favorites = repository.favorites.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        loadChannels()
    }

    private fun loadChannels() {
        viewModelScope.launch {
            val channels = M3uParser.parse(getApplication(), "playlist.m3u")
            _allChannels.value = channels
            _categories.value = channels.map { it.category }.distinct()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavorite(channel: Channel, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                repository.removeFavorite(channel.url)
            } else {
                repository.addFavorite(FavoriteChannel(url = channel.url, name = channel.name, category = channel.category))
            }
        }
    }
}
