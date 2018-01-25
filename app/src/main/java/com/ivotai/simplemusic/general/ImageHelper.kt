package com.ivotai.simplemusic.general

import android.content.ContentUris
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.ivotai.simplemusic.song.model.Song
import jp.wasabeef.blurry.Blurry

object ImageHelper {

    fun songToUri(song: Song): Uri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), song.albumId)


    fun getBitmapComposer(context: Context, song: Song): Blurry.BitmapComposer {
        val uri = ImageHelper.songToUri(song)
        val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
        return Blurry.with(context).radius(20)
                .sampling(8)
//                .color(Color.parseColor("#80dddddd"))
                .from(bitmap)
    }

}

