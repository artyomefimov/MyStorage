package com.artyomefimov.mystorage.presenter.list

import com.artyomefimov.mystorage.model.Product
import io.realm.Realm
import io.realm.RealmResults

class ProductListPresenter : ProductListContract.Presenter {

    private lateinit var view: ProductListContract.View

    override fun attach(view: ProductListContract.View) {
        this.view = view
        view.showProgress(false)
    }

    override fun loadProducts() {
        var products: RealmResults<Product>? = null
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                products = realm.where(Product::class.java)
                    .findAllAsync()
            }
            products?.addChangeListener { products ->
                view.loadDataSuccess(products)
            }
        }
    }

    override fun detach() {

    }
}