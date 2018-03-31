package com.itgrx.lib.data.compat.java8

object Nulls {

    @Throws(IllegalArgumentException::class)
    fun <T> validate(value: T): T {
        return validate(value, null)
    }

    @Throws(IllegalArgumentException::class)
    fun <T> validate(value: T?, valueName: String?): T {
        if (value == null) {
            if (valueName == null) {
                throw IllegalArgumentException("The value is null")
            } else {
                throw IllegalArgumentException("'" + valueName
                        + "' is null")
            }
        }

        return value
    }

    fun <T> complement(value: T?, complement: T): T {
        return value ?: complement
    }

    fun <T> equals(a: T?, b: T?): Boolean {
        return if (a == null) b == null else a == b
    }
}