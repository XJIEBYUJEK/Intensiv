package ru.androidschool.intensiv.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)

@Serializable
data class Company(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("origin_country")
    val originCountry: String?,
    @SerialName("logo_path")
    val logoPath: String?
)

@Serializable
data class Country(
    @SerialName("name")
    val name: String?,
    @SerialName("iso_3166_1")
    val iso: String?,
)

@Serializable
data class Language(
    @SerialName("name")
    val name: String?,
    @SerialName("iso_639_1")
    val iso: String?,
    @SerialName("english_name")
    val englishName: String?
)