package com.itgrx.lib.data.compat.java8

import java.util.NoSuchElementException

open class Union<T, U> @Throws(IllegalArgumentException::class)
protected constructor(value1: T?, value2: U?) {

    private var value1: T? = null
    private var value2: U? = null

    init {
        when {
            value1 == null -> this.value2 = Nulls.validate(value2)
            value2 == null -> this.value1 = Nulls.validate(value1)
            else -> throw IllegalArgumentException(
                    "Both of 'value1' and 'value2' are not null. One must be null.")
        }
    }

    val is1: Boolean
        get() = value1 != null


    @Throws(NoSuchElementException::class)
    fun get1(): T {
        if (!is1) {
            throw NoSuchElementException()
        }
        return value1!!
    }

    @Throws(NoSuchElementException::class)
    fun get2(): U {
        if (is1) {
            throw NoSuchElementException()
        }
        return value2!!
    }

    fun get1OrEmpty() = Optional.ofNullable(value1)

    fun get2OrEmpty() = Optional.ofNullable(value2)

    @Throws(IllegalArgumentException::class)
    open fun if1(consumer: (T) -> Unit): Union<T, U> {
        Nulls.validate(consumer)

        if (is1) {
            consumer(value1!!)
        }

        return this
    }

    open fun if2(consumer: (U) -> Unit): Union<T, U> {
        Nulls.validate(consumer)

        if (!is1) {
            consumer(value2!!)
        }

        return this
    }

    @Throws(IllegalArgumentException::class)
    fun <V, W> map(mapper1: Function<in T, out V>,
                   mapper2: Function<in U, out W>): Union<V, W> {
        Nulls.validate(mapper1)
        Nulls.validate(mapper2)

        return if (is1) {
            of1(mapper1.apply(value1!!))
        } else {
            of2(mapper2.apply(value2!!))
        }
    }

    @Throws(IllegalArgumentException::class)
    fun <V, W> flatMap(mapper1: Function<in T, Union<V, W>>,
                       mapper2: Function<in U, Union<V, W>>): Union<V, W> {
        Nulls.validate(mapper1)
        Nulls.validate(mapper2)

        return if (is1) {
            Nulls.validate(mapper1.apply(value1!!))
        } else {
            Nulls.validate(mapper2.apply(value2!!))
        }
    }

    fun <V> reduce(mapper1: Function<in T, out V>,
                   mapper2: Function<in U, out V>): V {
        Nulls.validate(mapper1)
        Nulls.validate(mapper2)

        return if (is1) {
            mapper1.apply(value1!!)
        } else {
            mapper2.apply(value2!!)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other !is Union<*, *>) {
            return false
        }

        val union = other as Union<*, *>?
        return if (is1) {
            if (union!!.is1) {
                value1 == union.value1
            } else {
                value1 == union.value2
            }
        } else {
            if (union!!.is1) {
                value2 == union.value1
            } else {
                value2 == union.value2
            }
        }
    }

    override fun hashCode(): Int {
        return if (is1) {
            value1!!.hashCode()
        } else {
            value2!!.hashCode()
        }
    }

    override fun toString(): String {
        return if (is1) {
            value1!!.toString()
        } else {
            value2!!.toString()
        }
    }

    companion object {

        @Throws(IllegalArgumentException::class)
        fun <T, U> of1(value: T): Union<T, U> {
            return Union(value, null)
        }

        @Throws(IllegalArgumentException::class)
        fun <T, U> of2(value: U): Union<T, U> {
            return Union(null, value)
        }
    }
}