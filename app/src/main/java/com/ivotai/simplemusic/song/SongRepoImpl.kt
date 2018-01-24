package com.ivotai.simplemusic.song

import android.content.Context
import android.provider.MediaStore
import io.reactivex.Single


class SongRepoImpl(private val context: Context) : SongRepo {

    override fun get(): Single<List<Song>> {
        return Single.create {
            val cursor = context.contentResolver.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    arrayOf(
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.ALBUM_ID
                    ),
                    null,
                    null,
                    MediaStore.Audio.Media.TITLE + " DESC")
            val result = ArrayList<Song>()
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    with(cursor) { result.add(Song(getString(0), getString(1), getString(2), getLong(3))) }
                } while (cursor.moveToNext())
            }
            cursor.close()
            it.onSuccess(result)
        }
    }

}