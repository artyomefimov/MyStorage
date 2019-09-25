package com.artyomefimov.mystorage.presenter.list

import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.presenter.BaseContract

interface ProductListContract {
    interface View: BaseContract.View {
        fun showProgress(isNeedToShow: Boolean)
        fun showErrorMessage(message: String)
        fun loadDataSuccess(products: List<Product>)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun loadProducts()
    }
}