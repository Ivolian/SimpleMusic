package com.ivotai.simplemusic.playing

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.ivotai.simplemusic.PlaySongEvent
import com.ivotai.simplemusic.R


class PlayFra : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fra_play, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }

    @Subscribe
    fun play(event: PlaySongEvent) {
        try {
//            RealPlayer.play(event.song)
//            if (player.isPlaying) {
//                player.reset()
//            }
//            val song = event.song
//            player.setDataSource(song.data)
//            player.prepare()
//            player.start()
        } catch (e: Exception) {
            ""
        }
    }

    private val player = MediaPlayer()

}