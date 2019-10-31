package com.ciet.desafio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ciet.desafio.service.model.movieDetailModel.CastResult
import com.ciet.desafio.service.model.movieDetailModel.MovieDetail
import com.ciet.desafio.service.repository.IMovieRepository
import com.ciet.desafio.service.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable

class MoviesDetailViewModel(movieRepository: IMovieRepository, movieID: Int) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val moviesDetail: LiveData<MovieDetail> by lazy {
        movieRepository.fetchMovieDetails(compositeDisposable, movieID)
    }

    val moviesMainCast: LiveData<CastResult> by lazy {
        movieRepository.fetchMovieCast(compositeDisposable, movieID)
    }

    override fun onCleared() {
        super.onCleared()
        //avoid memory leaks
        compositeDisposable.dispose()
    }
}