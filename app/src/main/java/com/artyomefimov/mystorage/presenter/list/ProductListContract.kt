package com.artyomefimov.mystorage.presenter.list

import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.presenter.BaseContract
import io.realm.RealmResults

interface ProductListContract {
    interface View: BaseContract.View {
        fun showProgress(isNeedToShow: Boolean)
        fun showErrorMessage(message: String)
        fun loadDataSuccess(products: RealmResults<Product>)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun loadProducts()
    }
}