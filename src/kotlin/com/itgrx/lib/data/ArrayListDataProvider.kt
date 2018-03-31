package com.itgrx.lib.data

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

    open class ArrayListDataProvider<T> : ListDataProvider<T> {

    private val data = mutableListOf<T>()

    protected val updatesSubject: PublishSubject<DataProvider.Event> = PublishSubject.create()

    override fun updates(): Observable<DataProvider.Event> = updatesSubject

    override fun getData(): MutableList<T> = data

    override fun size() = data.size

    override operator fun get(index: Int) = data[index]

    override fun add(element: T): Boolean {
        val res = data.add(element)
        updatesSubject.onNext(DataProvider.Inserted(data.size - 1, 1))
        return res
    }

    override fun add(index: Int, element: T) {
        data.add(index, element)
        updatesSubject.onNext(DataProvider.Inserted(index, 1))
    }

    override operator fun plusAssign(element: T) {
        add(element)
    }

    override operator fun plusAssign(elements: Collection<T>) {
        addAll(elements)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val position = data.size
        val res = data.addAll(elements)
        updatesSubject.onNext(DataProvider.Inserted(position, elements.size))
        return res
    }

    override fun removeAt(index: Int): T? {
        val res = data.removeAt(index)
        updatesSubject.onNext(DataProvider.Removed(index, 1))
        return res
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        val res = data.removeAll(elements)
        updatesSubject.onNext(DataProvider.AllChanged())
        return res
    }

    override fun clear() {
        data.clear()
        updatesSubject.onNext(DataProvider.AllChanged())
    }

}
