package com.artyomefimov.mystorage.view.detail

import android.view.MenuItem
import com.artyomefimov.mystorage.view.detail.viewstate.ViewState
import com.artyomefimov.mystorage.view.detail.viewstate.applyNewStateFor
import kotlinx.android.synthetic.main.fragment_product_detail.*

internal fun ProductDetailFragment.applyChangedState(changedState: ViewState, editItem: MenuItem?) {
    initialViewState = changedState
    applyNewStateFor(changedState, editItem, product_name_edit_text, price_edit_text)
}