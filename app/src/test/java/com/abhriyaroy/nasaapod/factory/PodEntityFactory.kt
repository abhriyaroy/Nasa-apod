package com.abhriyaroy.nasaapod.factory

import com.abhriyaroy.nasaapod.data.entity.PodEntity
import java.util.UUID.randomUUID

object PodEntityFactory {
    fun getVideoPodEntity() = PodEntity(
        randomUUID().toString(),
        randomUUID().toString(),
        randomUUID().toString(),
        "movie",
        randomUUID().toString(),
        randomUUID().toString(),
        randomUUID().toString()
    )

    fun getImagePodEntity() = PodEntity(
        randomUUID().toString(),
        randomUUID().toString(),
        randomUUID().toString(),
        "image",
        randomUUID().toString(),
        randomUUID().toString(),
        randomUUID().toString()
    )
}
