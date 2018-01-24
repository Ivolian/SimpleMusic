package com.ivotai.simplemusic.song

import android.content.ContentUris
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hwangjr.rxbus.RxBus
import com.ivotai.simplemusic.PlaySongEvent
import com.ivotai.simplemusic.R
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

        songAdapter.setOnItemClickListener { _, _, position -> songAdapter.getItem(position)
                .apply {
                    RxBus.get().post(PlaySongEvent(this!!))
                    setBg(song = this)
                }

        }

        loadSong()
    }

    private fun loadSong(){
        val songRepo = SongRepoImpl(context!!)


        GetSong(songRepo).execute().subscribe {
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
                    setBg(it.data!![2])
//                    loadingView.hide()
//                    retryView.hide()
                    songAdapter.setNewData(it.data)
                }
            }
        }
    }

    private fun setBg(song:Song){
        val id = song.albumId
        val uri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), id)
        val bitmap = BitmapFactory.decodeStream(activity!!.contentResolver.openInputStream(uri))
        Blurry.with(context) .radius(20)
                .sampling(8)
//                .color(Color.parseColor("#A6000000"))
                .from(bitmap).into(imageView);

//        val blur = BlurBuilder.blur(context,bitmap)
//        root.background = BitmapDrawable(blur)
    }



}