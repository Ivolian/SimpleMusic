package com.ivotai.simplemusic.song

import android.content.ContentUris
import android.net.Uri
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ivotai.simplemusic.R

class SongAdapter : BaseQuickAdapter<Song, BaseViewHolder>(R.layout.item_song, null) {

    override fun convert(helper: BaseViewHolder, item: Song) {
        helper.setText(R.id.tvArtist, item.artist)
        helper.setText(R.id.tvTitle, item.title)
        val uri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), item.albumId);
        Glide.with(mContext).load(uri).into(helper.getView(R.id.ivCover))
    }

}

