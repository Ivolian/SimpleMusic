package com.ivotai.simplemusic.general

import android.os.Bundle
import com.hwangjr.rxbus.RxBus

abstract class BaseFra : android.support.v4.app.Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
    }

    override fun onDestroy() {
        RxBus.get().unregister(this)
        super.onDestroy()
    }

}