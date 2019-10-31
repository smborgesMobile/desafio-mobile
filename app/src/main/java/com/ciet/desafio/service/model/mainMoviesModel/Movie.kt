package com.ciet.desafio.service.model.mainMoviesModel

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val movieID: Int,
    @SerializedName("poster_path")
    val movieImageUrl: String,
    @SerializedName("original_title")
    val movieTitle: String,
    @SerializedName("release_date")
    val movieDate: String
)