package com.appetiser.challenge.data

import android.content.Context
import android.content.SharedPreferences
import com.appetiser.challenge.views.MainActivity

/**
 * Created by Jp Cabrera on 3/26/2021.
 *
 * Centralized saving of data using sharedpref
 *
 * @param context to initialize sharedpref
 */
class CachedData(context: Context) {

    private val LAST_UPDATED_ACTIVITY_KEY = "lastUpdateKey"
    private val LAST_UPDATED_ID_KEY = "lastUpdateID"
    val PREFS_FILENAME = "cachedUpdate"
    var prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0);
    }

    var lastVisitedActivity: String?
        get() = prefs.getString(LAST_UPDATED_ACTIVITY_KEY, MainActivity::class.simpleName)
        set(value) = prefs.edit().putString(LAST_UPDATED_ACTIVITY_KEY, value).apply()

    var lastVisitedId: Int
        get() = prefs.getInt(LAST_UPDATED_ID_KEY, 0)
        set(value) = prefs.edit().putInt(LAST_UPDATED_ID_KEY, value).apply()
}