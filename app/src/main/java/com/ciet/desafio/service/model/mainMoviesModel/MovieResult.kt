package com.ciet.desafio.service.model.mainMoviesModel

import com.google.gson.annotations.SerializedName

data class MovieResult(@SerializedName("results") val result: ArrayList<Movie>)