package com.abhriyaroy.nasaapod.data.entity

import com.google.gson.annotations.SerializedName

data class PodEntity(
    @SerializedName("date")
    val date: String,
    @SerializedName("explanation")
    val description: String,
    @SerializedName("hdurl")
    val optionalAssetUrl: String?,
    @SerializedName("media_type")
    private val _mediaType: String,
    @SerializedName("service_version")
    val service: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val mediaUrl: String
) {
    val mediaType: MediaType
        get() = MediaType.valueOf(_mediaType.toUpperCase())
}

enum class MediaType {
    IMAGE,
    VIDEO
}
