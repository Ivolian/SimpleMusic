package com.ivotai.simplemusic.song.ui

import android.Manifest
import com.ivotai.simplemusic.general.AUF
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.rxkotlin.subscribeBy
import qiu.niorgai.StatusBarCompat

class SongAct : AUF() {

    override fun createFra() = SongFra()

    override fun doBeforeAddFra() {
        StatusBarCompat.translucentStatusBar(this,true);
        assurePermission()
    }

    private fun assurePermission() {
        RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribeBy { if (!it) finish() }
    }

}
