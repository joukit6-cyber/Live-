package com.example.util

import android.content.Context
import com.example.model.Channel
import java.io.BufferedReader
import java.io.InputStreamReader

object M3uParser {
    fun parse(context: Context, filename: String): List<Channel> {
        val channels = mutableListOf<Channel>()
        try {
            val inputStream = context.assets.open(filename)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var currentCategory = "Uncategorized"
            var currentName = ""

            reader.forEachLine { line ->
                val trimmed = line.trim()
                if (trimmed.startsWith("#EXTINF")) {
                    // example: #EXTINF:-1,AR: 24H Quran (Manchawi)
                    val split = trimmed.split(",")
                    if (split.size > 1) {
                        val name = split[1].trim()
                        if (name.contains("☘☘☘") || name.contains("---")) {
                            currentCategory = name.replace("☘", "").replace("★", "").replace("-", "").trim()
                            if (currentCategory.isEmpty()) currentCategory = name
                            currentName = "" // this is a category header, ignore the next url
                        } else {
                            currentName = name
                        }
                    }
                } else if (trimmed.isNotEmpty() && !trimmed.startsWith("#")) {
                    if (currentName.isNotEmpty()) {
                        channels.add(Channel(id = trimmed, name = currentName, url = trimmed, category = currentCategory))
                        currentName = ""
                    }
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return channels
    }
}
