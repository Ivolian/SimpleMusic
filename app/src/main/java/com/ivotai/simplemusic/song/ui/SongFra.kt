package com.ivotai.simplemusic.song.ui

import android.content.*
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivotai.simplemusic.MusicService
import com.ivotai.simplemusic.R
import com.ivotai.simplemusic.general.ImageHelper
import com.ivotai.simplemusic.player.Player
import com.ivotai.simplemusic.song.model.Song
import com.ivotai.simplemusic.song.repo.SongRepoImpl
import com.ivotai.simplemusic.song.useCase.GetSong
import io.reactivex.rxkotlin.subscribeBy
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.fra_song.*


class SongFra : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fra_song, container, false)
    }

    private val songAdapter = SongAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = songAdapter

        tvTitle.text = ""
        tvArtist.text = ""
        songAdapter.setOnItemClickListener { _, _, position ->
            val song = songAdapter.getItem(position)!!
                notifyUi(song)
            player!!.play(song)
        }


        loadSong()

        iivLast.setOnClickListener { player!!.playLast() }
        iivNext.setOnClickListener { player!!.playNext() }

    }

    var player: Player? = null


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            player = (service as MusicService.LocalBinder).service

        }

        override fun onServiceDisconnected(className: ComponentName) {
            player = null
        }
    }

    private fun loadSong() {
        val songRepo = SongRepoImpl(context!!)


        GetSong(songRepo).execute().subscribeBy {
            when {
                it.isSuccess() -> {

                    context!!.bindService(Intent(context, MusicService::class.java).apply {
                        this.putExtra("songs", it.data as ArrayList<Song>)
                    }, connection, Context.BIND_AUTO_CREATE)


//                    RealPlayer.song = it.data!!
                    setBg(it.data!![2])
//                    loadingView.hide()
//                    retryView.hide()
                    songAdapter.setNewData(it.data)
                }
            }
        }
    }

    private fun notifyUi(song:Song){
        setBg(song = song)
        ivAlbum.setImageURI(ImageHelper.songToUri(song = song))
        tvTitle.text = song.title
        tvArtist.text = song.artist
    }


    private fun setBg(song: Song) {
        val id = song.albumId
        val uri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), id)
        val bitmap = BitmapFactory.decodeStream(context!!.contentResolver.openInputStream(uri))
        Blurry.with(context).radius(20)
                .sampling(8)
//                .color(Color.parseColor("#80dddddd"))
                .from(bitmap).into(ivBg);

//        val blur = BlurBuilder.blur(context,bitmap)
//        root.background = BitmapDrawable(blur)

    }

}