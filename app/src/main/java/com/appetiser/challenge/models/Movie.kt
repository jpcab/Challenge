package com.appetiser.challenge.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET

/**
 * Created by Jp Cabrera on 3/26/2021.
 *
 *  Model used by Database
 *
 *  [displayDescription]  to get description based on [kind] and [wrapperType]
 *  [displayName]  to get item name based on [trackName] and [wrapperType]
 *  [displayPrice] to get item name based on [wrapperType]
 *  [displayPrice] to get price based on [wrapperType]
 *  [displayType] to get Type based on [wrapperType]
 *
 */

@Parcelize
@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var artworkUrl100: String?,
    var country: String?,
    var copyright: String?,
    var collectionViewUrl: String?,
    var previewUrl: String?,
    var releaseDate: String?,
    var artistId: String?,
    var collectionPrice: Double,
    var primaryGenreName: String?,
    var collectionName: String?,
    var artistViewUrl: String?,
    var collectionExplicitness: String?,
    var trackCount: Int,
    var artworkUrl60: String?,
    var wrapperType: String?,
    var artistName: String?,
    var currency: String?,
    var collectionId: String?,
    var collectionCensoredName: String?,
    var trackName: String?,
    var trackPrice: Double,
    var description: String?,
    var kind: String?,
    var longDescription: String?,
    var visitCount: Int = 0,
    var visitDate: Long
) : Parcelable {
    var displayDescription: String? = ""
        set(value) {
            if (wrapperType == "audiobook") {
                field = description.toString()
            } else if (wrapperType == "audiobook" && kind == "song") {
                field = "Not Specified"
            } else {
                field = longDescription
            }
        }
    var displayName: String? = ""
        set(value) {
            if (wrapperType == "audiobook") {
                field = collectionCensoredName.toString()
            } else if (wrapperType == "track") {
                field = trackName
            } else {
                field = "Not Specified"
            }
        }
    var displayPrice: Double = 0.0
        set(value) {
            if (wrapperType == "audiobook") {
                field = collectionPrice
            } else if (wrapperType == "track") {
                field = trackPrice
            } else {
                field = 0.0
            }
        }
    var displayType: String? = ""
        set(value) {
            if (wrapperType == "audiobook") {
                field = "audiobook"
            } else if (wrapperType == "track") {
                field = kind.toString()
            } else {
                field = "Not Specified"
            }
        }
}