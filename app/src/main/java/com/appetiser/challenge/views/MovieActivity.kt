package com.appetiser.challenge.views

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.appetiser.challenge.R
import com.appetiser.challenge.models.Movie
import com.appetiser.challenge.utils.DateUtil
import com.appetiser.challenge.viewmodels.MovieViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_movie.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jp Cabrera on 3/27/2021.
 *
 * This class is used to show details of the selected item from [MainActivity]
 */
class MovieActivity : BaseActivity() {

    private lateinit var movieViewModel: MovieViewModel
    private var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        movieViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(this.application)
            ).get(
                MovieViewModel::class.java
            )

        provideToolbar()
        provideContent()
    }

    /**
     * Provides content based on the id provided from intent.
     */
    private fun provideContent() {
        id = intent.extras?.get(MainActivity.TRACK_KEY) as Int

        movieViewModel.getMovie(id).observeOnce(this, Observer {

            movieViewModel.updateVisitedTimes(it)

            tv_visitedTimes.text = getString(R.string.visitedTimes, it.visitCount.toString())
            tv_visitedDate.text = context.getString(
                R.string.visitStatus,
                DateUtil.changeDateFormat(
                    DateUtil.convertMillisToDate(it.visitDate),
                    "MM/dd/yyyy hh:mm a"
                )
            )

            tvTrackName.text =
                getString(R.string.track_name, it.displayName)
            tvDescription.text = movieViewModel.HTMLEncode(it.displayDescription)
            tvReleaseDate.text = getString(
                R.string.date, DateUtil.dateFormatUpdate(
                    it.releaseDate
                )
            )
            tvType.text = getString(R.string.type, it.displayType)
            tvArtistName.text =
                getString(
                    R.string.artist,
                    it.artistName
                )
            tvGenre.text =
                getString(
                    R.string.genre,
                    it.primaryGenreName
                )
            tvPrice.text =
                getString(
                    R.string.price,
                    it.displayPrice,
                    it.currency
                )

            var circularProgressDrawable = CircularProgressDrawable(applicationContext)
            circularProgressDrawable.setColorSchemeColors(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent
            )
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.start()

            Glide
                .with(applicationContext)
                .load(it.artworkUrl100)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_launcher_foreground)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        circularProgressDrawable.stop()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        circularProgressDrawable.stop()
                        return false
                    }
                })
                .into(artwork)
        })
    }

    /**
     * Saves the track ID on [cachedData] to be used on the last visited activity
     */
    override fun onPause() {
        super.onPause()
        cachedData.lastVisitedId = id
    }
}
