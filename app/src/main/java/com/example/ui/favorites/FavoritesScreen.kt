package com.example.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.model.Channel
import com.example.ui.home.ChannelItem
import com.example.ui.viewmodel.MainViewModel

@Composable
fun FavoritesScreen(
    viewModel: MainViewModel,
    onNavigateToPlayer: (String) -> Unit
) {
    val favorites by viewModel.favorites.collectAsState()

    if (favorites.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("No favorites found", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(favorites) { favorite ->
                val channel = Channel(
                    id = favorite.url,
                    name = favorite.name,
                    url = favorite.url,
                    category = favorite.category
                )
                ChannelItem(
                    channel = channel,
                    isFavorite = true,
                    onToggleFavorite = { viewModel.toggleFavorite(channel, true) },
                    onClick = { onNavigateToPlayer(channel.url) }
                )
            }
        }
    }
}
