package com.ivotai.simplemusic.player

import android.media.MediaPlayer
import com.hwangjr.rxbus.RxBus
import com.ivotai.simplemusic.event.SongChangeEvent
import com.ivotai.simplemusic.song.model.Song
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
        } catch (e: Exception) {
            ""
        }

    }

    override fun play(index: Int) {
        currentIndex = index
        play()
    }

    override fun play(song: Song) {
        play(songs.indexOf(song))
    }

    override fun playLast() {
        if (hasLast) {
            currentIndex--
            play()
            RxBus.get().post(SongChangeEvent(currentSong()))
        }

    }

    override fun playNext() {
        if (hasNext) {
            currentIndex++
            play()
            RxBus.get().post(SongChangeEvent(currentSong()))
        }
    }

    fun pause() {
        if (mPlayer.isPlaying) {
            mPlayer.pause()

        }

    }

    override fun nextSong(): Song {
        var nextIndex = currentIndex + 1
        if (currentIndex > songs.size - 1) {
            nextIndex = 0
        }
        return songs[nextIndex]
    }

    override fun lastSong(): Song {
        var lastIndex = currentIndex - 1
        if (lastIndex < 0) {
            lastIndex = songs.size - 1
        }
        return songs[lastIndex]
    }

    override fun currentSong(): Song {
       return  songs[currentIndex]
    }

    override fun seekTo(progress: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun releasePlayer() {
        mPlayer.reset()
        mPlayer.release()
    }


}
