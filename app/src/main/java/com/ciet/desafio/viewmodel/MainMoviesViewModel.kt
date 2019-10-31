package com.ciet.desafio.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ciet.desafio.service.model.mainMoviesModel.MovieResult
import com.ciet.desafio.service.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable

class MainMoviesViewModel(movieRepository: MovieRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val movies: LiveData<MovieResult> = movieRepository.fetchMovie(compositeDisposable)
    val popularMovies: LiveData<MovieResult> = movieRepository.fetchPopularMovies(compositeDisposable)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}