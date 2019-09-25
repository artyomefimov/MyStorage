package com.artyomefimov.mystorage.view.detail.viewstate

import com.artyomefimov.mystorage.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class ViewsStateControllerTest {
    @Test
    fun testGetEditingStateInCaseOfEmptyNameAndPrice() {
        val controller =
            ViewStateController()
        val product = Product()

        assertEquals(ViewState.EditingState, controller.getInitialViewState(product))
    }

    @Test
    fun testGetStableStateInCaseOfNonEmptyNameAndPrice() {
        val controller =
            ViewStateController()
        val product = Product(name = "some word", price = 1.0)

        assertEquals(ViewState.StableState, controller.getInitialViewState(product))
    }

    @Test
    fun testGetDifferentViewStateEveryTime() {
        val controller =
            ViewStateController()

        val product = Product()

        val firstCallState = controller.getInitialViewState(product)
        val secondCallState = controller.getNewState()

        assertEquals(ViewState.EditingState, firstCallState)
        assertEquals(ViewState.StableState, secondCallState)
    }
}