package com.abhriyaroy.nasaapod.exception


private const val podFetchExceptionMessage = "Something went wrong while fetching the photo of the day"

class PodFetchException : Exception(podFetchExceptionMessage)
