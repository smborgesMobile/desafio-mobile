package com.ciet.desafio.service.rest

import com.ciet.desafio.service.model.movieDetailModel.CastResult
import com.ciet.desafio.service.model.movieDetailModel.MovieDetail
import com.ciet.desafio.service.model.mainMoviesModel.MovieResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDBDao {
    @GET("movie/upcoming")
    fun getUpcomingMovie(): Single<MovieResult>

    @GET("movie/popular")
    fun getPopularMovie(): Single<MovieResult>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") id: Int): Single<MovieDetail>

    @GET("movie/{movie_id}/credits")
    fun getMovieCast(@Path("movie_id") id: Int): Single<CastResult>
}