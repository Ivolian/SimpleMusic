package com.ivotai.simplemusic.song

import com.ivotai.simplemusic.ViewState1
import io.reactivex.Observable

class GetSong(private val userRepo: SongRepo) {
    fun execute(): Observable<ViewState1<List<Song>>> = userRepo.get()
            .toObservable()
            .map { ViewState1(data = it) }
            .onErrorReturn { ViewState1(error = it) }
            .startWith(ViewState1())
}