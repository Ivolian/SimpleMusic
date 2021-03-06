package com.ivotai.simplemusic.song.ui

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ivotai.simplemusic.R
import com.ivotai.simplemusic.event.SongSwitchEvent
import com.ivotai.simplemusic.general.ImageHelper
import com.ivotai.simplemusic.general.MusicVisualizer
import com.ivotai.simplemusic.song.model.Song

class SongAdapter : BaseQuickAdapter<Song, BaseViewHolder>(R.layout.item_song, null) {

    var current: Song? = null

    override fun convert(helper: BaseViewHolder, item: Song) {
        helper.setText(R.id.tvArtist, item.artist)
        helper.setText(R.id.tvTitle, item.title)
        Glide.with(mContext).load(ImageHelper.songToUri(item)).into(helper.getView(R.id.ivAlbum))
        highlightPlaying(helper, item)
    }

    private fun highlightPlaying(helper: BaseViewHolder, item: Song) {
        val visualizer = helper.getView<MusicVisualizer>(R.id.visualizer)
        if (item == current) {
            val ivAlbum = helper.getView<ImageView>(R.id.ivAlbum)
            val it = ivAlbum.drawable as? BitmapDrawable
            it?.let {
                Palette.from(it.bitmap).generate { palette ->
                    ""
                    palette.lightVibrantSwatch?.rgb?.let {
                        helper.getView<TextView>(R.id.tvTitle).setTextColor(it)
                        visualizer.visibility = View.VISIBLE
                        visualizer.paint.color = it
                    }
                }
            }
        } else {
            helper.getView<TextView>(R.id.tvTitle).setTextColor(Color.WHITE)
            visualizer.visibility = View.GONE
        }
    }

    fun songSwitchAction(event: SongSwitchEvent) {
        current = event.song
        notifyDataSetChanged()
    }

}

