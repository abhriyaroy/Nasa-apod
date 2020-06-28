package com.abhriyaroy.nasaapod.util

object VideoUrlUtil{
    fun getYoutubeVideoIdFromUrl(videoUrl : String) : String{
        return videoUrl.split("/").let {
            it[it.size - 1].split("?")[0]
        }
    }
}
