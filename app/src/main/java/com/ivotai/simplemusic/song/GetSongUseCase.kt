package com.ivotai.simplemusic.song

import com.ivotai.simplemusic.ViewState
import io.reactivex.Observable

class GetSongUseCase(private val songRepo: SongRepo) {
    fun execute(): Observable<ViewState<List<Song>>> = songRepo.get()
            .toObservable()
            .map { ViewState(data = it) }
            .onErrorReturn { ViewState(error = it) }
            .startWith(ViewState())
}