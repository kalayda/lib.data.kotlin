package com.itgrx.lib.dataprovider

interface MutableDataProvider<T> : DataProvider<T>, MutableList<T>