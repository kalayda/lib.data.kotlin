package com.itgrx.lib.data.compat.java8

import java.util.NoSuchElementException

class Optional<T> private constructor(x: T, y: Null?) : Union<T, Optional.Null>(x, y) {

    class Null private constructor() {
        companion object {
            val INSTANCE = Null()
        }
    }

    val isPresent: Boolean
        get() = is1

    @Throws(NoSuchElementException::class)
    fun get(): T {
        return get1()
    }

    @Throws(IllegalArgumentException::class)
    override fun if1(consumer: (T) -> Unit): Optional<T> {
        return super.if1(consumer) as Optional<T>
    }

    override fun if2(consumer: (Null) -> Unit): Optional<T> {
        return super.if2(consumer) as Optional<T>
    }

    @Throws(IllegalArgumentException::class)
    fun ifPresent(consumer: (T) -> Unit) {
        if1(consumer)
    }

    fun orElse(other: T): T {
        return if (isPresent) {
            get()
        } else {
            other
        }
    }

    @Throws(IllegalArgumentException::class)
    fun orElseGet(supplier: Supplier<T>): T {
        Nulls.validate(supplier)

        return if (isPresent) {
            get()
        } else {
            supplier.get()
        }
    }

    fun <X : Throwable> orElseThrow(
            exceptionSupplier: Supplier<out X>): T {
        Nulls.validate(exceptionSupplier)

        return if (isPresent) {
            get()
        } else {
            throw exceptionSupplier.get()
        }
    }

    fun filter(predicate: Predicate<in T>): Optional<T> {
        Nulls.validate(predicate)

        return if (isPresent && predicate.test(get())) {
            this
        } else {
            empty()
        }
    }

    fun <U> map(mapper: Function<in T, out U>): Optional<U> {
        Nulls.validate(mapper)

        return if (!isPresent) {
            empty()
        } else {
            ofNullable(mapper.apply(get()))
        }
    }

    fun <U> flatMap(mapper: Function<in T, Optional<U>>): Optional<U> {
        Nulls.validate(mapper)

        return if (!isPresent) {
            empty()
        } else {
            Nulls.validate(mapper.apply(get()))
        }
    }


    companion object {
        private val EMPTY = Optional<Any?>(null, Null.INSTANCE)

        fun <T> of(value: T): Optional<T> {
            return Optional(value, null)
        }

        fun <T> empty(): Optional<T> {
            return EMPTY as Optional<T>
        }

        fun <T> ofNullable(value: T?): Optional<T> {
            return if (value == null) {
                empty()
            } else {
                of(value)
            }
        }
    }
}
