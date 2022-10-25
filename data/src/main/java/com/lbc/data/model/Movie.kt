package com.lbc.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lbc.data.util.DATABASE_NAME
import java.util.*

//storedTime, favorite 때문에 mapper 써도 될거 같지만 소규모 프로젝트라 mapper 없이 작업했습니다.
@Entity(tableName = DATABASE_NAME)
data class Movie(
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    val overview: String,
    val poster_path: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @ColumnInfo
    var storedTime: Date = Date(),
    var favorite: Boolean = false
)
