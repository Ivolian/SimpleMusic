package com.ivotai.simplemusic.song

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hwangjr.rxbus.RxBus
import com.ivotai.simplemusic.PlaySongEvent
import com.ivotai.simplemusic.R
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

        songAdapter.setOnItemClickListener { adapter, view, position -> songAdapter.getItem(position)
                .apply {
                    RxBus.get().post(PlaySongEvent(this!!))
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
//                    loadingView.hide()
//                    retryView.hide()
                    songAdapter.setNewData(it.data)
                }
            }
        }
    }



}