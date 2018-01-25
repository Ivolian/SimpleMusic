package com.ivotai.simplemusic.song.repo

import com.ivotai.simplemusic.song.model.Song
import io.reactivex.Single

interface SongRepo {
    fun get(): Single<List<Song>>
}