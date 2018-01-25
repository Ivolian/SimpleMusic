package com.ivotai.simplemusic

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ivotai.simplemusic.player.Player
import com.ivotai.simplemusic.player.RealPlayer
import com.ivotai.simplemusic.song.model.Song


class MusicService(private val player: Player = RealPlayer()) : Service(), Player by player {


    // ============== binder ==============

    inner class LocalBinder : Binder() {
        val service get() = this@MusicService
    }

    private val mBinder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        val songs = intent.getSerializableExtra("songs")
        initPlayer(songs = songs as ArrayList<Song>)
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


    private fun initPlayer(songs: List<Song>) {
        player as RealPlayer
        player.songs = songs

    }

}
