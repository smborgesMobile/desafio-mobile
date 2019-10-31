package com.ciet.desafio.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciet.desafio.R
import com.ciet.desafio.service.model.movieDetailModel.MovieActor
import kotlinx.android.synthetic.main.main_cast_layout.view.*


class MainCastAdapter(
    private val castList: ArrayList<MovieActor>, private var context: Context
) :
    RecyclerView.Adapter<MainCastAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actor = castList[position]
        holder.actorImageUrlImageView?.let { loadImage(actor.actorImageUrl, it) }
        holder.actorRealNameTextView?.text = actor.actorName
        holder.actorPaperNameTextView?.text = actor.actorPaper

    }

    private fun loadImage(imageUrl: String, imageView: ImageView) {
        val baseUrl = "http://image.tmdb.org/t/p/w500"
        Glide.with(context)
            .load(baseUrl + imageUrl)
//            .placeholder(R.drawable.place_holder)
            .into(imageView)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.main_cast_layout, null)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val actorImageUrlImageView: ImageView? = itemView.imageView_actor_profile
        val actorRealNameTextView: TextView? = itemView.textView_actor_real_name
        val actorPaperNameTextView: TextView? = itemView.textView_actor_paper_name
    }
}

