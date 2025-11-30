package com.lbc.movieapp.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Movie(
    val id: Long,
    val adult: Boolean,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val storedTime: Date = Date(),
    val isFavorite: Boolean = false
) : Parcelable
