package com.itgrx.lib.dataprovider

import io.reactivex.Observable

interface DataProvider<T> : List<T> {

    interface Event

    data class Inserted(
            val position: Int,
            val count: Int
    ) : Event

    data class Changed(
            val position: Int,
            val count: Int
    ) : Event

    data class AllChanged(val ignore: Any? = null): Event

    data class Moved(
            val from: Int,
            val to: Int
    ) : Event

    data class Removed(
            val position: Int,
            val count: Int
    ) : Event

    fun updates(): Observable<Event>

}