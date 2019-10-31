package com.ciet.desafio.service.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ciet.desafio.service.model.mainMoviesModel.MovieResult
import com.ciet.desafio.service.model.movieDetailModel.CastResult
import com.ciet.desafio.service.model.movieDetailModel.MovieDetail
import com.ciet.desafio.service.rest.MovieDBDao
import io.reactivex.disposables.CompositeDisposable

class MovieRepository(private val apiService: MovieDBDao): IMovieRepository {
    private lateinit var imdbNetworkDataSource: MovieNetwork

    companion object {
        private val TAG = MovieRepository::class.simpleName
    }

    override fun fetchMovie(compositeDisposable: CompositeDisposable): LiveData<MovieResult> {
        imdbNetworkDataSource = MovieNetwork(apiService, compositeDisposable)
        imdbNetworkDataSource.fetchMovie()
        Log.d(TAG, "fetch Movies: ${imdbNetworkDataSource.downloadMovieResponse}")
        return imdbNetworkDataSource.downloadMovieResponse
    }

    override fun fetchPopularMovies(compositeDisposable: CompositeDisposable): LiveData<MovieResult> {
        imdbNetworkDataSource = MovieNetwork(apiService, compositeDisposable)
        imdbNetworkDataSource.fetchPopularMovie()
        Log.d(TAG, "fetch popular Movies: ${imdbNetworkDataSource.downloadMovieResponse}")
        return imdbNetworkDataSource.downloadMovieResponse
    }

    override fun fetchMovieDetails(compositeDisposable: CompositeDisposable, id: Int): LiveData<MovieDetail> {
        imdbNetworkDataSource = MovieNetwork(apiService, compositeDisposable)
        imdbNetworkDataSource.fetchMovieDetail(id)
        Log.d(TAG, "fetch Movies detail: ${imdbNetworkDataSource.downloadMovieResponse}")
        return imdbNetworkDataSource.downloadMovieDetailResponse
    }

    override fun fetchMovieCast(
        compositeDisposable: CompositeDisposable, id: Int): LiveData<CastResult> {
        imdbNetworkDataSource = MovieNetwork(apiService, compositeDisposable)
        imdbNetworkDataSource.fetchMovieCast(id)
        Log.d(TAG, "fetch Movies cast: ${imdbNetworkDataSource.downloadMovieResponse}")
        return imdbNetworkDataSource.downloadMovieCastResponse
    }
}