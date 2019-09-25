package com.artyomefimov.mystorage.view.detail.viewstate

import android.view.MenuItem
import android.widget.EditText
import com.artyomefimov.mystorage.R
import com.artyomefimov.mystorage.model.Product

/**
 * Handles view state and returns a new state according to a current state
 */
class ViewStateController {
    private var currentState: ViewState =
        ViewState.StableState

    fun setInitialViewState(viewState: ViewState) {
        currentState = viewState
    }

    fun getInitialViewState(product: Product): ViewState {
        return if (product.name.isEmpty() or (0.0 == product.price)) {
            currentState = ViewState.EditingState
            currentState
        } else {
            currentState
        }
    }

    fun getNewState(): ViewState {
        return if (currentState == ViewState.EditingState) {
            currentState = ViewState.StableState
            currentState
        } else {
            currentState = ViewState.EditingState
            currentState
        }
    }
}

enum class ViewState(
    val menuIcon: Int,
    val isEnabled: Boolean
) {
    EditingState(R.drawable.ic_action_done, true),
    StableState(R.drawable.ic_action_edit, false)
}

fun applyNewStateFor(viewState: ViewState, editItem: MenuItem?, vararg editTexts: EditText) {
    viewState.apply {
        editItem?.setIcon(menuIcon)
        editTexts.forEach {it.isEnabled = isEnabled}
    }
}