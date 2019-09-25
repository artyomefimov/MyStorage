package com.artyomefimov.mystorage.presenter.detail

import com.artyomefimov.mystorage.presenter.BaseContract

interface ProductDetailContract {
    interface View : BaseContract.View {
        fun hideFragment()
        fun setNameDataToView(name: String)
        fun setPriceDataToView(price: String)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun saveProduct(name: String, price: String)
        fun deleteProduct()
        fun isNewProduct(): Boolean
        fun setProductDataToViews()
    }
}