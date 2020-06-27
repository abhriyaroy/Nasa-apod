package com.abhriyaroy.nasaapod.exception


private const val podFetchExceptionMessage = "Something went wrong while fetching movies"

class PodFetchException : Exception(podFetchExceptionMessage)
