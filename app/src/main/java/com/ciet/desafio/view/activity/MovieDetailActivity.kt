package com.ciet.desafio.view.activity

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ciet.desafio.R
import com.ciet.desafio.service.model.movieDetailModel.CastResult
import com.ciet.desafio.service.model.movieDetailModel.MovieActor
import com.ciet.desafio.service.model.movieDetailModel.MovieDetail
import com.ciet.desafio.service.repository.IMovieRepository
import com.ciet.desafio.service.repository.MovieRepository
import com.ciet.desafio.service.rest.TheMovieDBClient
import com.ciet.desafio.view.adapter.MainCastAdapter
import com.ciet.desafio.viewmodel.MoviesDetailViewModel
import kotlinx.android.synthetic.main.activity_movie_datail.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesDetailViewModel
    private lateinit var movieRepository: IMovieRepository
    private lateinit var mainCastAdapter: MainCastAdapter

    var mainCastList = arrayListOf<MovieActor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_datail)
        textView_detail_description.movementMethod = ScrollingMovementMethod()

        mainCastAdapter = MainCastAdapter(mainCastList, this)
        recyclerView_details_actors.adapter = mainCastAdapter
        recyclerView_details_actors.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val movieId = intent.getIntExtra("MOVIE_DETAIL", 1)
        val apiService = TheMovieDBClient.getClient()
        movieRepository = MovieRepository(apiService)
        viewModel = getViewModel(movieId)
        viewModel.moviesDetail.observe(this, Observer {
            bindUI(it)
        })

        viewModel.moviesMainCast.observe(this, Observer {
            bindMainCast(it)
        })
    }

    private fun bindMainCast(cast: CastResult?) {
        cast?.let {
            mainCastList.clear()
            mainCastList.addAll(it.result)
            mainCastAdapter.notifyDataSetChanged()
        }
    }

    private fun bindUI(movieDetail: MovieDetail?) {
        if (movieDetail != null) {
            fillTheMovieDetails(movieDetail)
        }
    }

    private fun getViewModel(movieId: Int): MoviesDetailViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MoviesDetailViewModel(movieRepository, movieId) as T
            }
        })[MoviesDetailViewModel::class.java]
    }

    private fun fillTheMovieDetails(movieDetail: MovieDetail) {
        fillTheListOfGenres(movieDetail)
        textView_detail_description.text = movieDetail.detailsOverview
        textView_detail_title.text = movieDetail.detailsTitle
        textView_detail_year.text = getYearFromCompleteDate(movieDetail.detailsDate)

        val baseUrl = "http://image.tmdb.org/t/p/w500"
        Glide.with(this)
            .load(baseUrl + movieDetail.detailsImageUrl)
            .into(imageView_detail_movie_image)
    }

    private fun getYearFromCompleteDate(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyy-MM-dd")
        val localDate = LocalDate.parse(date, formatter)
        return localDate?.year.toString()
    }

    private fun fillTheListOfGenres(movieDetail: MovieDetail) {
        var result: String? = ""

        val genresList = movieDetail?.detailsGenres
        genresList.forEachIndexed { index, element ->
            result += if (index < genresList.size - 1) {
                "${element.name}, "
            } else {
                "${element.name}."
            }
        }
        textView_detail_time_and_type.text = "${movieDetail.detailsRuntime} m | $result"
    }

}
