package com.ivotai.simplemusic.song

import android.content.Context
import android.provider.MediaStore
import io.reactivex.Single

class SongRepoImpl(private val context: Context) : SongRepo {

    override fun get(): Single<List<Song>> {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST
                ),
                null,
                null,
                MediaStore.Audio.Media.TITLE + " DESC")
        val songs = ArrayList<Song>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val song = Song(cursor.getString(0), cursor.getLong(1), cursor.getString(2))
                songs.add(song)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return Single.just(songs)
    }

}