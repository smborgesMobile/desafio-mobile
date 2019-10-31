package com.ciet.desafio.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ciet.desafio.R
import com.ciet.desafio.service.model.mainMoviesModel.Movie
import com.ciet.desafio.service.model.mainMoviesModel.MovieResult
import com.ciet.desafio.service.repository.MovieRepository
import com.ciet.desafio.service.rest.MovieDBDao
import com.ciet.desafio.service.rest.TheMovieDBClient
import com.ciet.desafio.view.adapter.MoviesAdapter
import com.ciet.desafio.viewmodel.MainMoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IViewHolderClickListener {
    private lateinit var mViewModel: MainMoviesViewModel
    lateinit var mMovieRepository: MovieRepository
    private var mMoviesList: ArrayList<Movie> = arrayListOf()
    private lateinit var mMovieAdapter: MoviesAdapter
    private var mSelectedButton: SelectedButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSelectedButton = SelectedButton.UPCOMING

        bindUpComingData()
        val recyclerView = recyclerViewMovies
        mMovieAdapter = MoviesAdapter(mMoviesList, this, this)
        recyclerView.adapter = mMovieAdapter
        recyclerView.layoutManager =
            GridLayoutManager(this, resources.getInteger(R.integer.number_of_columns))

        manageUpcomingButtonClick()

        btn_upcoming_movies.setOnClickListener {
            manageUpcomingButtonClick()
            bindUpComingData()
            mSelectedButton = SelectedButton.UPCOMING
        }

        btn_popular_movies.setOnClickListener {
            managePopularButtonClick()
            bindPopularData()
            mSelectedButton = SelectedButton.POPULAR
        }

        swipe_to_refresh.setOnRefreshListener {
            if (mSelectedButton == SelectedButton.POPULAR) {
                bindPopularData()
            } else {
                bindUpComingData()
            }
        }
    }


    private fun bindPopularData() {
        val apiService: MovieDBDao = TheMovieDBClient.getClient()
        mMovieRepository = MovieRepository(apiService)
        mViewModel = getViewModel()
        mViewModel.popularMovies.observe(this, Observer {
            bindUI(it)
        })
    }

    private fun bindUpComingData() {
        val apiService: MovieDBDao = TheMovieDBClient.getClient()
        mMovieRepository = MovieRepository(apiService)
        mViewModel = getViewModel()
        mViewModel.movies.observe(this, Observer {
            bindUI(it)
        })
    }

    private fun bindUI(movie: MovieResult?) {
        movie?.let {
            mMoviesList.clear()
            mMoviesList.addAll(it.result)
            mMovieAdapter.notifyDataSetChanged()
            swipe_to_refresh.isRefreshing = false
        }
    }

    private fun getViewModel(): MainMoviesViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainMoviesViewModel(mMovieRepository) as T
            }
        })[MainMoviesViewModel::class.java]
    }

    private fun manageUpcomingButtonClick() {
        btn_upcoming_movies.setTextColor(Color.BLACK)
        btn_upcoming_movies.background = getDrawable(R.drawable.text_view_border_left_selected)

        btn_popular_movies.setTextColor(Color.WHITE)
        btn_popular_movies.background = getDrawable(R.drawable.text_view_border_right)

        txt_type_movies.text = getString(R.string.main_screen_upcoming)
    }

    private fun managePopularButtonClick() {
        btn_popular_movies.setTextColor(Color.BLACK)
        btn_popular_movies.background = getDrawable(R.drawable.text_view_border_right_selected)

        btn_upcoming_movies.setTextColor(Color.WHITE)
        btn_upcoming_movies.background = getDrawable(R.drawable.text_view_border_left)
        txt_type_movies.text = getString(R.string.main_screen_popular_movies)
    }

    override fun onItemClick(item: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE_DETAIL", item.movieID)
        startActivity(intent)
    }

    internal enum class SelectedButton {
        POPULAR, UPCOMING
    }
}
