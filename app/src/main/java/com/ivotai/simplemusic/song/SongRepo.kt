package com.ivotai.simplemusic.song

import io.reactivex.Single

interface SongRepo {
    fun get(): Single<List<Song>>
}