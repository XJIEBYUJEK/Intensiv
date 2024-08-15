package ru.androidschool.intensiv.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.BuildConfig

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
) {
    @SerialName("profile_path")
    var profilePath: String? = null
        get() = "${BuildConfig.IMAGE_URL}$SCALE$field"
    companion object{
        const val SCALE = "w500"
    }
}
