package com.calyrsoft.ucbp1.features.github.data.error

import java.lang.Exception

sealed class DataException : Exception() {
    object Network: DataException()
    object HttpNotFound: DataException()
    object NoContent: DataException()
    data class Unknown(override val message: String): DataException()
}
