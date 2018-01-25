package com.ivotai.simplemusic.song.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ConvertUtils
import com.ivotai.simplemusic.MusicService
import com.ivotai.simplemusic.R
import com.ivotai.simplemusic.general.ImageHelper
import com.ivotai.simplemusic.player.Player
import com.ivotai.simplemusic.song.model.Song
import com.ivotai.simplemusic.song.repo.SongRepoImpl
import com.ivotai.simplemusic.song.useCase.GetSong
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fra_song.*




class SongFra : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fra_song, container, false)
    }

    private val songAdapter = SongAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        iivLast.setOnClickListener { player.playLast() }
        iivNext.setOnClickListener { player.playNext() }
        loadSong()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = songAdapter

        tvTitle.text = ""
        tvArtist.text = ""
        ivAlbum.setBackgroundColor(Color.TRANSPARENT)
        songAdapter.setOnItemClickListener { _, _, position ->
            val song = songAdapter.getItem(position)!!
            notifyUi(song)
            player.play(song)
        }

        recyclerView.addItemDecoration(
                HorizontalDividerItemDecoration.Builder(context)
                        .color(Color.parseColor("#409e9e9e"))
                        .size(1)
                        .margin(ConvertUtils.dp2px(96f),0)
                        .build())
    }

    lateinit var player: Player

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            player = (service as MusicService.LocalBinder).service
        }

        override fun onServiceDisconnected(className: ComponentName) {

        }
    }

    private fun bindMusicService(songs: List<Song>) {
        context!!.bindService(
                Intent(context, MusicService::class.java).apply {
                    this.putExtra("songs", songs as ArrayList<Song>)
                },
                connection,
                Context.BIND_AUTO_CREATE)
    }

    private fun loadSong() {

        val songRepo = SongRepoImpl(context!!)


        GetSong(songRepo).execute().subscribeBy {
            when {
                it.isSuccess() -> {
                    songAdapter.setNewData(it.data)
                    bindMusicService(it.data!!)
                }
            }
        }
    }

    private fun notifyUi(song: Song) {
        ImageHelper.getBitmapComposer(context!!, song).into(ivBg)
        ivAlbum.setImageURI(ImageHelper.songToUri(song = song))
        tvTitle.text = song.title
        tvArtist.text = song.artist
    }

}