package com.ivotai.simplemusic.general

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ivotai.simplemusic.R

abstract class AUF : AppCompatActivity() {

    protected abstract fun createFra(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        doBeforeAddFra()
        if (savedInstanceState == null) {
            addFra()
        }
    }

    private fun addFra() {
        supportFragmentManager.beginTransaction()
                .add(R.id.container, createFra())
                .commit()
    }

    protected open fun doBeforeAddFra() {

    }

}