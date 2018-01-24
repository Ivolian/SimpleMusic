package com.ivotai.simplemusic.song

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ivotai.simplemusic.R

class SongAdapter : BaseQuickAdapter<Song, BaseViewHolder>(R.layout.item_song, null) {

    override fun convert(helper: BaseViewHolder, item: Song) {
        helper.setText(R.id.tvArtist, item.artist)
        helper.setText(R.id.tvDuration, item.duration.toString())
        helper.setText(R.id.tvTitle, item.title)
    }

}

