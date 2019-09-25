package com.artyomefimov.mystorage.presenter.list

import com.artyomefimov.mystorage.repository.ProductRepository
import io.reactivex.disposables.Disposable

class ProductListPresenter(
    private val productRepository: ProductRepository,
    private var subscription: Disposable? = null
) : ProductListContract.Presenter {

    private lateinit var view: ProductListContract.View

    override fun attach(view: ProductListContract.View) {
        this.view = view
        view.showProgress(false)
    }

    override fun loadProducts() {
        subscription = productRepository.findAll()
            .doOnSubscribe { view.showProgress(true) }
            .subscribe(
                {products ->
                    view.showProgress(false)
                    view.loadDataSuccess(products)
                },
                {e ->
                    view.showProgress(false)
                    view.showMessage(e.message!!)
                }
            )
    }

    override fun detach() {
        subscription?.dispose()
    }
}