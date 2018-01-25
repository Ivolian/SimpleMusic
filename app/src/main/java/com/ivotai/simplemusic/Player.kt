package com.ivotai.simplemusic

interface Player {

    fun play()
    fun playNext()
    fun playLast()
    fun seekTo(progress:Int)

}