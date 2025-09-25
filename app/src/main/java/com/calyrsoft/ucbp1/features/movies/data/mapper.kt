package com.calyrsoft.ucbp1.features.movies.data

import com.calyrsoft.ucbp1.features.movies.data.database.entity.MovieEntity
import com.calyrsoft.ucbp1.features.movies.domain.model.MovieModel

fun MovieEntity.toModel(): MovieModel {
    return MovieModel(
        title = title ?: "",
        pathUrl = pathUrl ?: ""
    )
}

fun MovieModel.toEntity(): MovieEntity {
    val entity = MovieEntity()
    entity.title = title
    entity.pathUrl = pathUrl
    return entity
}