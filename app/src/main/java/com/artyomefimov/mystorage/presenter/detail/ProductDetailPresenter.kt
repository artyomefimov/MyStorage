package com.artyomefimov.mystorage.presenter.detail

import com.artyomefimov.mystorage.R
import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.repository.ProductRepository
import com.artyomefimov.mystorage.view.detail.viewstate.ViewState
import com.artyomefimov.mystorage.view.detail.viewstate.ViewStateController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

class ProductDetailPresenter(
    val product: Product,
    private val productRepository: ProductRepository,
    private val viewStateController: ViewStateController = ViewStateController(),
    private val incorrectNameMessage: Int = R.string.incorrect_name,
    private val incorrectPriceMessage: Int = R.string.incorrect_price,
    private var updateSubscription: Disposable? = null,
    private var deleteSubscription: Disposable? = null,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : ProductDetailContract.Presenter {
    private lateinit var view: ProductDetailContract.View

    override fun attach(view: ProductDetailContract.View) {
        this.view = view
        view.showProgress(false)
    }

    override fun setProductDataToViews() {
        view.setNameDataToView(product.name)

        val price = if (product.price == 0.0) "" else product.price.toString()
        view.setPriceDataToView(price)
        view.setImageDataToView(product.imagePath)
    }

    override fun isNewProduct(): Boolean =
        product.id.isEmpty()

    fun getNewState(): ViewState =
        viewStateController.getNewState()

    fun getInitialViewState(): ViewState =
        viewStateController.getInitialViewState(product)

    fun setInitialViewState(viewState: ViewState) =
        viewStateController.setInitialViewState(viewState)

    override fun setProductImagePath(imagePathUri: String) {
        product.imagePath = imagePathUri
    }

    fun areNameAndPriceCorrect(name: String, price: String): Boolean {
        return when {
            name.isEmpty() or name.isBlank() -> {
                view.showMessage(incorrectNameMessage)
                false
            }
            price.toDoubleOrNull() == null -> {
                view.showMessage(incorrectPriceMessage)
                false
            }
            else -> true
        }
    }

    override fun saveProduct(name: String, price: String) {
        if (areNameAndPriceCorrect(name, price)) {
            applyDataFromViews(name, price)
            generateIdIfProductIsNew()

            updateSubscription = productRepository.save(product)
                .doOnSubscribe { view.showProgress(true) }
                .subscribe(
                    {
                        view.showProgress(false)
                        view.showMessage(R.string.success_message)
                    },
                    { e ->
                        view.showProgress(false)
                        view.showMessage(e.message!!)
                    }
                )
            compositeDisposable.add(updateSubscription!!)
        }
    }

    private fun applyDataFromViews(name: String, price: String) {
        product.name = name.trim()
        product.price = price.toDouble()
    }

    private fun generateIdIfProductIsNew() {
        if (isNewProduct())
            product.id = UUID.randomUUID().toString()
    }

    override fun deleteProduct() {
        deleteSubscription = productRepository.delete(product)
            .doOnSubscribe { view.showProgress(true) }
            .subscribe(
                {
                    view.showProgress(false)
                    view.hideFragment()
                },
                { e ->
                    view.showProgress(false)
                    view.showMessage(e.message!!)
                }
            )
        compositeDisposable.add(deleteSubscription!!)
    }

    override fun detach() {
        compositeDisposable.dispose()
    }
}