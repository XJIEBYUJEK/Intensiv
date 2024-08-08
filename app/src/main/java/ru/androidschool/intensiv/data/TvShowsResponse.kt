package ru.androidschool.intensiv.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowsResponse(
    @SerialName("page")
    var page: Int,
    @SerialName("results")
    var results: List<TvShow>,
    @SerialName("total_results")
    var totalResults: Int,
    @SerialName("total_pages")
    var totalPages: Int
)
