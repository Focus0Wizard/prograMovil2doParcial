package com.calyrsoft.ucbp1.features.movies.data.api.dto

import com.google.gson.annotations.SerializedName

data class MovieListDto(
    @SerializedName("results")
    val movies: Array<MovieDto>
)