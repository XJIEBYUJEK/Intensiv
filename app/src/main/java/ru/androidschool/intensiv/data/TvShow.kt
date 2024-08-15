package ru.androidschool.intensiv.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.BuildConfig

@Serializable
data class TvShow(
    @SerialName("adult")
    val isAdult: Boolean?,
    @SerialName("backdrop_path")
    val backDropPath: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>?,
    @SerialName("id")
    val id: Int?,
    @SerialName("origin_country")
    val originCountry: List<String>?,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?,
    @SerialName("original_name")
    val originalName: String?,
    @SerialName("first_air_date")
    val firstAirDate: String?,
    @SerialName("name")
    val name: String?
) {
    val rating: Float
        get() = ratingCalculation(voteAverage)
    @SerialName("poster_path")
    var posterPath: String? = null
        get() = "${BuildConfig.IMAGE_URL}$SCALE$field"
    companion object{
        const val SCALE = "w500"
    }
}
