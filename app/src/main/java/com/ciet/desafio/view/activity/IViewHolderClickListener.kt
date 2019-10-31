package com.ciet.desafio.view.activity

import com.ciet.desafio.service.model.mainMoviesModel.Movie

interface IViewHolderClickListener {
    fun onItemClick(item: Movie)
}