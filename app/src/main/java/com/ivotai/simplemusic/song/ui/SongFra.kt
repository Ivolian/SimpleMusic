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
            setBg(song = song)
            ivAlbum.setImageURI(ImageHelper.songToUri(song = song))
            tvTitle.text = song.title
            tvArtist.text = song.artist
        }


        loadSong()

        iivLast.setOnClickListener { mPlaybackService!!.playLast() }
        iivNext.setOnClickListener { mPlaybackService!!.playNext() }

    }

    var mPlaybackService: MusicService? = null

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mPlaybackService = (service as MusicService.LocalBinder).service

        }

        override fun onServiceDisconnected(className: ComponentName) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mPlaybackService = null

        }
    }

    private fun loadSong() {
        val songRepo = SongRepoImpl(context!!)


        GetSong(songRepo).execute().subscribeBy {
            when {
                it.isLoading() -> {

//                    loadingView.show()
//                    retryView.hide()
                }
                it.isError() -> {
                    ""
//                    loadingView.hide()
//                    retryView.show()
                }
                it.isSuccess() -> {

                    context!!.bindService(Intent(context, MusicService::class.java).apply {
                        this.putExtra("songs", it.data as ArrayList<Song>)
                    }, mConnection, Context.BIND_AUTO_CREATE)


//                    RealPlayer.song = it.data!!
                    setBg(it.data!![2])
//                    loadingView.hide()
//                    retryView.hide()
                    songAdapter.setNewData(it.data)
                }
            }
        }
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