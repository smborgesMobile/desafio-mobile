package com.ciet.desafio.service.model.movieDetailModel

import com.google.gson.annotations.SerializedName

data class MovieActor(
    @SerializedName("profile_path") var actorImageUrl: String,
    @SerializedName("name") var actorName: String,
    @SerializedName("character") var actorPaper: String
)
