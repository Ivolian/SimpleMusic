package com.ivotai.simplemusic.song.model

import java.io.Serializable

data class Song(
        val title: String,
        val artist: String,
        val data: String,
        val albumId: Long
): Serializable

