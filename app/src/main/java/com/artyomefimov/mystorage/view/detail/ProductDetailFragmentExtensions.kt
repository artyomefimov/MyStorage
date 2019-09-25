package com.artyomefimov.mystorage.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.artyomefimov.mystorage.App
import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.presenter.detail.ProductDetailPresenter
import com.artyomefimov.mystorage.view.detail.viewstate.ViewState
import com.artyomefimov.mystorage.view.detail.viewstate.applyNewStateFor
import kotlinx.android.synthetic.main.fragment_product_detail.*

internal fun ProductDetailFragment.initPresenter(product: Product) {
    productPresenter = ProductDetailPresenter(product, App.repository(activity!!))
}

internal fun ProductDetailFragment.getInitialViewStateFrom(savedInstanceState: Bundle?) {
    val viewStateFromBundle = savedInstanceState?.getSerializable(ProductDetailFragment.VIEW_STATE)

    initialViewState = if (viewStateFromBundle != null) {
        viewStateFromBundle as ViewState
    } else ViewState.StableState
}

internal fun ProductDetailFragment.applyChangedState(changedState: ViewState, editItem: MenuItem?) {
    initialViewState = changedState
    applyNewStateFor(changedState, editItem, product_name_edit_text, price_edit_text)
}

internal fun ProductDetailFragment.chooseImageForProduct() {
    val intent = Intent().apply {
        type = "image/*"
        action = Intent.ACTION_OPEN_DOCUMENT
        addCategory(Intent.CATEGORY_OPENABLE)
    }
    startActivityForResult(
        intent,
        ProductDetailFragment.PICK_IMAGE_REQUEST_CODE
    )
}