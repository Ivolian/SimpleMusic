package com.ivotai.simplemusic

import android.media.MediaPlayer
import com.ivotai.simplemusic.song.Song
import java.io.IOException
import java.util.*


object MusicPlayer {

    private var mPlayer = MediaPlayer()

    val isPlaying get() = mPlayer.isPlaying

    val progress get() = mPlayer.currentPosition

    var mPlayList: List<Song> = ArrayList()
    var currentIndex = -1

    val isInit get() = currentIndex != -1
    val hasLast get() = isInit && currentIndex > 0
    val hasNext get() = isInit && currentIndex < mPlayList.size - 1

    fun play(): Boolean {
//        if (isPaused) {
//            mPlayer!!.start()
//            notifyPlayStatusChanged(true)
//            return true
//        }
        val song = mPlayList[currentIndex]
        try {
            mPlayer.reset()
            mPlayer.setDataSource(song.data)
            mPlayer.prepare()
            mPlayer.start()
        } catch (e: IOException) {
            return false
        }
        return true
    }

    fun play(index:Int): Boolean {
        currentIndex = index
        return play()
    }

    fun play(song:Song): Boolean {
        return play(mPlayList.indexOf(song))
    }

    fun playLast(): Boolean {
        if (hasLast) {
            currentIndex--
            play()
            return true
        }
        return false
    }

    fun playNext(): Boolean {
        if (hasNext) {
            currentIndex++
            play()
            return true
        }
        return false
    }

    fun pause(): Boolean {
        if (mPlayer.isPlaying) {
            mPlayer.pause()
            return true
        }
        return false
    }

    fun releasePlayer() {
        mPlayer.reset()
        mPlayer.release()
    }


}
