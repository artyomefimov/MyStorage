package com.artyomefimov.mystorage.presenter

class BaseContract {
    interface Presenter<in T> {
        fun attach(view: T)
        fun detach()
    }

    interface View
}