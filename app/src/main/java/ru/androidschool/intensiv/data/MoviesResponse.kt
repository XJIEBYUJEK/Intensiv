package ru.androidschool.intensiv.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse (
    @SerialName("page")
    var page: Int,
    @SerialName("results")
    var results: List<Movie>,
    @SerialName("total_results")
    var totalResults: Int,
    @SerialName("total_pages")
    var totalPages: Int,
)
