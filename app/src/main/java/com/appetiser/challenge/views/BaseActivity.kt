package com.appetiser.challenge.views

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.appetiser.challenge.data.CachedData
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Provides a base class that extends [AppCompatActivity]
 */
abstract class BaseActivity : AppCompatActivity() {

    lateinit var context: Context
    lateinit var cachedData: CachedData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        cachedData = CachedData(context)
    }

    /**
     * Used to provide toolbar with back button
     * Used the [R.layout.toolbar] before using this method
     */
    fun provideToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Back"

        toolbar.setNavigationIcon(com.appetiser.challenge.R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { finish() }
    }

    /**
     * A utility function used to only observe a livedata once
     */
    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    /**
     * Saves the class name on [cachedData]
     */
    override fun onPause() {
        super.onPause()
        cachedData.lastVisitedActivity = javaClass.simpleName
    }
}