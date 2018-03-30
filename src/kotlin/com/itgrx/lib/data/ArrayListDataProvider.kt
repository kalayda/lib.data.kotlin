package com.itgrx.lib.dataprovider

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

open class ArrayListDataProvider<T>() : MutableDataProvider<T>, ArrayList<T>() {

    protected val updatesSubject: PublishSubject<DataProvider.Event> = PublishSubject.create()

    override fun updates(): Observable<DataProvider.Event> = updatesSubject

    override fun add(element: T): Boolean {
        val res = super.add(element)
        updatesSubject.onNext(DataProvider.Inserted(size - 1, 1))
        return res
    }

    override fun add(index: Int, element: T) {
        super.add(index, element)
        updatesSubject.onNext(DataProvider.Inserted(index, 1))
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val position = size
        val res = super.addAll(elements)
        updatesSubject.onNext(DataProvider.Inserted(position, elements.size))
        return res
    }

    override fun removeAt(index: Int): T {
        val res = super.removeAt(index)
        updatesSubject.onNext(DataProvider.Removed(index, 1))
        return res
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        val res = super.removeAll(elements)
        updatesSubject.onNext(DataProvider.AllChanged())
        return res
    }

    override fun clear() {
        super.clear()
        updatesSubject.onNext(DataProvider.AllChanged())
    }

}
