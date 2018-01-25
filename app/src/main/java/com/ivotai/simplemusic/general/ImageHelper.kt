package com.ivotai.simplemusic.general

import android.content.ContentUris
import android.net.Uri
import com.ivotai.simplemusic.song.model.Song

object ImageHelper {

  fun songToUri(song: Song): Uri= ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), song.albumId)

}

