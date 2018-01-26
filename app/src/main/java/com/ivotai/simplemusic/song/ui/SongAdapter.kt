package com.ivotai.simplemusic.song.ui

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ivotai.simplemusic.R
import com.ivotai.simplemusic.general.ImageHelper
import com.ivotai.simplemusic.song.model.Song

class SongAdapter : BaseQuickAdapter<Song, BaseViewHolder>(R.layout.item_song, null) {

    var current: Song? = null

    override fun convert(helper: BaseViewHolder, item: Song) {
        helper.setText(R.id.tvArtist, item.artist)
        helper.setText(R.id.tvTitle, item.title)
        Glide.with(mContext).load(ImageHelper.songToUri(item)).into(helper.getView(R.id.ivAlbum))

        if ( item == current) {
            val ivAblum = helper.getView<ImageView>(R.id.ivAlbum)
            var b = ivAblum.drawable as? BitmapDrawable
            b?.let {
                Palette.from(it.bitmap).generate { palette ->
                    ""
                    palette.lightVibrantSwatch?.rgb?.let {
                        helper.getView<TextView>(R.id.tvTitle).setTextColor(it)
                    }
                }
            }

        }else{
            helper.getView<TextView>(R.id.tvTitle).setTextColor(Color.WHITE)

        }
    }

}

