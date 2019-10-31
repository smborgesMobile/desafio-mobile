package com.ciet.desafio.service.repository

import androidx.lifecycle.LiveData
import com.ciet.desafio.service.model.mainMoviesModel.MovieResult
import com.ciet.desafio.service.model.movieDetailModel.CastResult
import com.ciet.desafio.service.model.movieDetailModel.MovieDetail
import io.reactivex.disposables.CompositeDisposable

interface IMovieRepository {
    fun fetchMovie(compositeDisposable: CompositeDisposable): LiveData<MovieResult>
    fun fetchPopularMovies(compositeDisposable: CompositeDisposable): LiveData<MovieResult>
    fun fetchMovieDetails(compositeDisposable: CompositeDisposable, id: Int): LiveData<MovieDetail>
    fun fetchMovieCast(compositeDisposable: CompositeDisposable, id: Int): LiveData<CastResult>
}