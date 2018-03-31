package com.itgrx.lib.data

interface ListContainer<T> {

    fun getData(): MutableList<T>

    fun size(): Int

    operator fun get(index: Int): T?

    fun add(element: T): Boolean

    fun add(index: Int, element: T)

    fun addAll(elements: Collection<T>): Boolean

    fun removeAt(index: Int): T?

    fun removeAll(elements: Collection<T>): Boolean

    operator fun plusAssign(element: T)

    operator fun plusAssign(elements: Collection<T>)

    fun clear()

}