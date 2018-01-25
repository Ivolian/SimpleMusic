package com.ivotai.simplemusic.player

interface Player {

    fun play()
    fun playNext()
    fun playLast()
    fun seekTo(progress:Int)

}