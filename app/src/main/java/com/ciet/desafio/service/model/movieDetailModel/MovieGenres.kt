package com.ciet.desafio.service.model.movieDetailModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MovieGenres(@SerializedName("id") var id: Int, @SerializedName("name") var name: String) :
    Serializable