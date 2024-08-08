package ru.androidschool.intensiv.data

import android.content.pm.PackageInstaller.InstallConstraintsResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    @SerialName("gender")
    val gender: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String?,
    @SerialName("original_name")
    val originalName: String?,
    @SerialName("character")
    val character: String?,
    @SerialName("order")
    val order: Int
){
    @SerialName("profile_path")
    var profilePath: String? = null
        get() = "https://image.tmdb.org/t/p/w500$field"
}
