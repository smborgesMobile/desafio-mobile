package com.ciet.desafio.service.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ciet.desafio.service.model.movieDetailModel.CastResult
import com.ciet.desafio.service.model.movieDetailModel.MovieDetail
import com.ciet.desafio.service.model.mainMoviesModel.MovieResult
import com.ciet.desafio.service.rest.MovieDBDao
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieNetwork(
    private val apiService: MovieDBDao,
    private val compositeDisposable: CompositeDisposable
) {

    companion object {
        private val TAG = MovieNetwork::class.simpleName
    }

    private val _networkState = MutableLiveData<NetworkState>()

    private val _downloadMovieResponse = MutableLiveData<MovieResult>()
    val downloadMovieResponse: LiveData<MovieResult>
        get() = _downloadMovieResponse

    private val _downloadMovieDetail = MutableLiveData<MovieDetail>()
    val downloadMovieDetailResponse: LiveData<MovieDetail>
        get() = _downloadMovieDetail

    private val _downloadMovieCast = MutableLiveData<CastResult>()
    val downloadMovieCastResponse: LiveData<CastResult>
        get() = _downloadMovieCast


    fun fetchMovie() {

        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getUpcomingMovie()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d(TAG, "fetching movies ${it.result}")
                        _downloadMovieResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.d(TAG, "Failed to retrieve movies list: " + it.message)
                    })
        )
    }

    fun fetchPopularMovie() {
        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d(TAG, "fetching popular movies ${it.result}")
                        _downloadMovieResponse.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.d(TAG, "Fail to retrieve popular movies: " + it.message)
                    })
        )
    }

    fun fetchMovieDetail(id: Int) {
        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d(TAG, "fetching movies detail $it")
                        _downloadMovieDetail.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.d(TAG, "fail to retrieve movie detail: " + it.message)
                    })
        )
    }

    fun fetchMovieCast(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getMovieCast(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d(TAG, "fetching $it")
                        _downloadMovieCast.postValue(it)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.d(TAG, "Fail to retrieve movie tag " + it.message)
                    })
        )
    }
}