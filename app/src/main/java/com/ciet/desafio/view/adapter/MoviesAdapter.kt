package com.ciet.desafio.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciet.desafio.R
import com.ciet.desafio.service.model.mainMoviesModel.Movie
import com.ciet.desafio.view.activity.IViewHolderClickListener
import kotlinx.android.synthetic.main.movie_item_layout.view.*


class MoviesAdapter(
    private val repos: ArrayList<Movie>,
    private var context: Context,
    private var viewHolderClickListener: IViewHolderClickListener
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = repos[position]
        loadImage(movie.movieImageUrl, holder.movieImageUrl)
        holder.movieTitle.text = movie.movieTitle
        holder.movieDate.text = movie.movieDate
        holder.itemView.setOnClickListener {
            viewHolderClickListener.onItemClick(movie)
        }

    }

    private fun loadImage(imageUrl: String, imageView: ImageView) {
        val baseUrl = "http://image.tmdb.org/t/p/w500"
        Glide.with(context)
            .load(baseUrl + imageUrl)
            .into(imageView)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.movie_item_layout, null)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val movieImageUrl = itemView.main_screen_thumb_image
        val movieTitle = itemView.main_screen_txt_title
        val movieDate = itemView.main_screen_txt_date
    }
}

