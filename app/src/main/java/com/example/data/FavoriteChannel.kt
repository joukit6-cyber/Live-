package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_channels")
data class FavoriteChannel(
    @PrimaryKey
    val url: String,
    val name: String,
    val category: String
)
