package com.ivotai.simplemusic.player

import android.media.MediaPlayer
import com.ivotai.simplemusic.song.model.Song
import java.io.IOException
import java.util.*


class RealPlayer : Player {


        var songs: List<Song> = ArrayList()
        var currentIndex = -1


    private var mPlayer = MediaPlayer()

    val isPlaying get() = mPlayer.isPlaying

    val progress get() = mPlayer.currentPosition


    val isInit get() = currentIndex != -1
    val hasLast get() = isInit && currentIndex > 0
    val hasNext get() = isInit && currentIndex < songs.size - 1

   override fun play() {
//        if (isPaused) {
//            mPlayer!!.start()
//            notifyPlayStatusChanged(true)
//            return true
//        }
        val song = songs[currentIndex]
        try {
            mPlayer.reset()
            mPlayer.setDataSource(song.data)
            mPlayer.prepare()
            mPlayer.start()
        } catch (e: IOException) {

        }

    }

   override fun play(index:Int) {
        currentIndex = index
         play()
    }

    override  fun play(song: Song)  {
         play(songs.indexOf(song))
    }

    override fun playLast() {
        if (hasLast) {
            currentIndex--
            play()

        }

    }

    override fun playNext()  {
        if (hasNext) {
            currentIndex++
            play()

        }

    }

    fun pause() {
        if (mPlayer.isPlaying) {
            mPlayer.pause()

        }

    }

    override fun seekTo(progress: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun releasePlayer() {
        mPlayer.reset()
        mPlayer.release()
    }


}
