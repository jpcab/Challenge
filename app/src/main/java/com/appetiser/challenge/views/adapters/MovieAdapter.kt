package com.appetiser.challenge.views.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.appetiser.challenge.R
import com.appetiser.challenge.models.Movie
import com.appetiser.challenge.utils.DateUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.row_movie.view.*


/**
 * Created by Jp Cabrera on 3/26/2021.
 *
 * Class extends [ListAdapter] to be used by Movies list
 * @constructor initializes the given params [context] , [movieClickListener]
 */
class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieViewHolder> {

    private var inflater: LayoutInflater
    private var movieClickListener: OnItemClickListener
    private var context: Context

    companion object {

        /**
         * Creates DiffUtil callback
         */
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    constructor(context: Context, movieClickListener: OnItemClickListener) : super(DIFF_CALLBACK) {
        inflater = LayoutInflater.from(context)
        this.movieClickListener = movieClickListener
        this.context = context
    }

    /**
     * An interface to be used as a callback from clicking an item
     */
    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = inflater.inflate(R.layout.row_movie, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)

        if (movie.visitDate == 0L) {
            holder.visitStatus.text = context.getString(R.string.havent_check_item)
        } else {
            holder.visitStatus.text = context.getString(
                R.string.visitStatus,
                DateUtil.changeDateFormat(
                    DateUtil.convertMillisToDate(movie.visitDate),
                    "MM/dd/yyyy hh:mm a"
                )
            )
        }

        holder.title.text =
            context.getString(
                R.string.track_name, movie.displayName
            )
        holder.price.text =
            context.getString(
                R.string.price, movie.displayPrice, movie.currency
            )
        holder.genre.text =
            context.getString(R.string.genre, movie.primaryGenreName)

        holder.itemBounds.setOnClickListener {
            movieClickListener.onItemClick(movie)
        }

        Glide
            .with(context)
            .load(movie.artworkUrl60)
            .centerCrop()
            .error(R.drawable.ic_error)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progress.visibility = GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progress.visibility = GONE
                    return false
                }
            })
            .into(holder.artwork)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.title
        val price = itemView.price
        val genre = itemView.genre
        val artwork = itemView.artwork
        val itemBounds = itemView.itemBounds
        val visitStatus = itemView.visitStatus
        val progress = itemView.progress
    }
}