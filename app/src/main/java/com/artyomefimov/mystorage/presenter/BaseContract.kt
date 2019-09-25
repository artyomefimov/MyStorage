package com.artyomefimov.mystorage.presenter

class BaseContract {
    interface Presenter<in T> {
        fun attach(view: T)
        fun detach()
    }

    interface View {
        fun showProgress(isNeedToShow: Boolean)
        fun showMessage(message: String)
        fun showMessage(messageResId: Int)
    }
}