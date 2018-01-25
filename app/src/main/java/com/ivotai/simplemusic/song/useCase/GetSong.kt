package com.ivotai.simplemusic.song.useCase

import com.ivotai.simplemusic.general.ViewState
import com.ivotai.simplemusic.song.model.Song
import com.ivotai.simplemusic.song.repo.SongRepo
import io.reactivex.Observable

class GetSong(private val songRepo: SongRepo) {
    fun execute(): Observable<ViewState<List<Song>>> = songRepo.get()
            .toObservable()
            .map { ViewState(data = it) }
            .onErrorReturn { ViewState(error = it) }
            .startWith(ViewState())
}