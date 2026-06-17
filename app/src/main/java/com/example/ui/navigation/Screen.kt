package com.example.ui.navigation

import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

object Routes {
    const val HOME = "home"
    const val FAVORITES = "favorites"
    const val PLAYER = "player/{url}"

    fun playerRoute(url: String): String {
        return "player/${URLEncoder.encode(url, StandardCharsets.UTF_8.toString())}"
    }

    fun decodeUrl(encodedUrl: String): String {
        return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
    }
}
