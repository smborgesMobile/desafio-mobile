package com.ciet.desafio.service.model.movieDetailModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieDetail(
    @SerializedName("backdrop_path") var detailsImageUrl: String,
    @SerializedName("title") var detailsTitle: String,
    @SerializedName("release_date") var detailsDate: String,
    @SerializedName("genres") var detailsGenres: ArrayList<MovieGenres>,
    @SerializedName("runtime") var detailsRuntime: String,
    @SerializedName("overview") var detailsOverview: String
) : Serializable