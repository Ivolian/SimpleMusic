package com.ivotai.simplemusic.main

import android.Manifest
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.ivotai.simplemusic.R
import com.ivotai.simplemusic.play.PlayFra
import com.ivotai.simplemusic.song.SongFra
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        assurePermission()
        initViewPager()
    }

    private fun initViewPager() {
        viewPager.offscreenPageLimit = 1
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount() = 2
            override fun getItem(position: Int) = if (position == 0) SongFra() else PlayFra()
        }
    }

    private fun assurePermission() {
        RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribeBy { if (!it) finish() }
    }

}
