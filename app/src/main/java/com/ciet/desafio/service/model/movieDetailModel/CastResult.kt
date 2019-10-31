package com.ciet.desafio.service.model.movieDetailModel

import com.google.gson.annotations.SerializedName

data class CastResult(@SerializedName("cast") val result: ArrayList<MovieActor>)