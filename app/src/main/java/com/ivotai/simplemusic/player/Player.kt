package com.ivotai.simplemusic.player

import com.ivotai.simplemusic.song.model.Song

interface Player {

    fun play()
    fun play(index:Int)
    fun play(song:Song)
    fun playNext()
    fun playLast()
    fun seekTo(progress:Int)
    fun nextSong():Song
    fun lastSong():Song
    fun currentSong():Song
}