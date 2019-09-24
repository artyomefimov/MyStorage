package com.artyomefimov.mystorage.presenter.detail

import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.presenter.BaseContract

interface ProductDetailContract {
    interface View : BaseContract.View {
        // todo define view operations
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun saveProduct(callUpdateService: (product: Product) -> Unit)
    }
}