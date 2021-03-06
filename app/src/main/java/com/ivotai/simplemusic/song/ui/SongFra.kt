package com.ivotai.simplemusic.song.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.hwangjr.rxbus.annotation.Subscribe
import com.ivotai.simplemusic.MusicService
import com.ivotai.simplemusic.R
import com.ivotai.simplemusic.event.SongSwitchEvent
import com.ivotai.simplemusic.general.BaseFra
import com.ivotai.simplemusic.general.ImageHelper
import com.ivotai.simplemusic.general.Key
import com.ivotai.simplemusic.player.Player
import com.ivotai.simplemusic.song.model.Song
import com.ivotai.simplemusic.song.repo.SongRepoImpl
import com.ivotai.simplemusic.song.useCase.GetSong
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fra_song.*


class SongFra : BaseFra() {

    override fun layoutId() = R.layout.fra_song

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        addListener()
        loadSong()

        ivAlbum.setBackgroundColor(Color.TRANSPARENT)
        tvTitle.text = ""
        tvArtist.text = ""
    }

    private val songAdapter = SongAdapter()

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = songAdapter

        songAdapter.setOnItemClickListener { _, _, position -> player.play(songAdapter.getItem(position)!!) }

        recyclerView.addItemDecoration(
                HorizontalDividerItemDecoration.Builder(context)
                        .color(Color.parseColor("#809e9e9e"))
                        .size(1)
                        .margin(ConvertUtils.dp2px(96f), 0)
                        .build())
    }

    private fun addListener() {
        iivLast.setOnClickListener {
            player.playLast()
        }
        iivNext.setOnClickListener {
            player.playNext()
        }
    }

    private fun loadSong() {
        val songRepo = SongRepoImpl(context!!)
        GetSong(songRepo).execute().subscribeBy {
            when {
                it.isSuccess() -> {
                    songAdapter.setNewData(it.data)
                    bindService(it.data!!)

                }
            }
        }
    }

    lateinit var player: Player

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            player = (service as MusicService.LocalBinder).service
            player.play(0)
        }

        override fun onServiceDisconnected(className: ComponentName) {

        }
    }

    private fun bindService(songs: List<Song>) {
        context!!.bindService(
                Intent(context, MusicService::class.java).apply {
                    this.putExtra(Key.SONGS, songs as ArrayList<Song>)
                },
                connection,
                Context.BIND_AUTO_CREATE)
    }

    @Subscribe
    fun songSwitchAction(event: SongSwitchEvent) {
        val song = event.song
        ImageHelper.getBitmapComposer(context!!, song)?.into(ivBg)
        ivAlbum.setImageURI(ImageHelper.songToUri(song = song))
        tvTitle.text = song.title
        tvTitle.isSelected = true
        tvArtist.text = song.artist
        songAdapter.songSwitchAction(event)
    }

}