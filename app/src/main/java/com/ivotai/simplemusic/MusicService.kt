package com.ivotai.simplemusic

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ivotai.simplemusic.song.Song



class MusicService : Service(), Player {


    // ============== binder ==============

    inner class LocalBinder : Binder() {
        val service get() = this@MusicService
    }

    private val mBinder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        val songs = intent.getSerializableExtra("songs")
        return mBinder
    }


    // ============== binder ==============

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    // ============== binder ==============

    private val musicPlayer = MusicPlayer()

    private fun initPlayer(songs: List<Song>) {
        musicPlayer.songs = songs
        musicPlayer.currentIndex = -1
    }

    // ============== binder ==============
    override fun play() {
    }

    override fun playNext() {
    }

    override fun playLast() {
    }

    override fun seekTo(progress: Int) {
    }
}
