package com.ivotai.simplemusic.song

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivotai.simplemusic.R
import com.tbruyelle.rxpermissions2.RxPermissions
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

        RxPermissions(activity as Activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({ granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        // Oups permission denied
                    }
                })

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