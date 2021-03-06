package com.abhriyaroy.nasaapod.util

import com.abhriyaroy.nasaapod.util.Status.*

data class ResourceResult<out T>(val status: Status, val data: T?, val error: Exception?) {
    companion object {
        fun <T> success(data: T?): ResourceResult<T> {
            return ResourceResult(
                SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: Exception?): ResourceResult<T> {
            return ResourceResult(ERROR, null, error)
        }

        fun <T> loading(data: T? = null): ResourceResult<T> {
            return ResourceResult(
                LOADING,
                data,
                null
            )
        }
    }
}
