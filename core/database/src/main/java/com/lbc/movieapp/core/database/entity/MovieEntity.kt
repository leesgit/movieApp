package com.lbc.movieapp.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val storedTime: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
