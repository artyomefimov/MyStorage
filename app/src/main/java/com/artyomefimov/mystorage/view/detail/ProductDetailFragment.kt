package com.artyomefimov.mystorage.view.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.artyomefimov.mystorage.App
import com.artyomefimov.mystorage.R
import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.presenter.detail.ProductDetailContract
import com.artyomefimov.mystorage.presenter.detail.ProductDetailPresenter
import com.artyomefimov.mystorage.view.detail.viewstate.ViewState
import com.artyomefimov.mystorage.view.utils.showSnackbarWithAction
import com.artyomefimov.mystorage.view.utils.showSnackbarWithMessage
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_product_detail.*

class ProductDetailFragment : Fragment(), ProductDetailContract.View {
    companion object {
        private const val PRODUCT = "product"
        private const val VIEW_STATE = "view_state"

        @JvmStatic
        fun newInstance(product: Product) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PRODUCT, product)
                }
            }
    }

    private lateinit var productPresenter: ProductDetailPresenter
    internal lateinit var initialViewState: ViewState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val product = arguments?.getSerializable(PRODUCT) as Product

        productPresenter = ProductDetailPresenter(product, App.repository(activity!!))

        val viewStateFromBundle = savedInstanceState?.getSerializable(VIEW_STATE)
        initialViewState = if (viewStateFromBundle != null) {
            viewStateFromBundle as ViewState
        } else ViewState.StableState

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_product_detail, menu)

        val editItem = menu.findItem(R.id.action_edit)

        productPresenter.getInitialViewState().apply {
            applyChangedState(this, editItem)
        }

        editItem?.setOnMenuItemClickListener {
            if (productPresenter.areNameAndPriceCorrect(
                    product_name_edit_text.text.toString(),
                    price_edit_text.text.toString()
                )
            ) {
                productPresenter.getNewState().apply {
                    applyChangedState(this, editItem)
                }
            }

            return@setOnMenuItemClickListener true
        }
        val deleteItem = menu.findItem(R.id.action_delete)!!

        deleteItem.setOnMenuItemClickListener {
            if (productPresenter.isNewProduct())
                showSnackbarWithMessage(R.string.product_is_new)
            else
                showSnackbarWithAction(R.string.confirm_deletion_message, android.R.string.yes) {
                    productPresenter.deleteProduct()
                }

            return@setOnMenuItemClickListener true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_product_detail,
        container,
        false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productPresenter.attach(this)
        productPresenter.setInitialViewState(initialViewState)

        fab_update_product.setOnClickListener {
            productPresenter.saveProduct(
                product_name_edit_text.text.toString(),
                price_edit_text.text.toString()
            )
        }

        productPresenter.setProductDataToViews()
    }

    override fun setNameDataToView(name: String) {
        product_name_edit_text.setText(name)
    }

    override fun setPriceDataToView(price: String) {
        price_edit_text.setText(price)
    }

    override fun showMessage(message: String) {
        showSnackbarWithMessage(message)
    }

    override fun showMessage(messageResId: Int) {
        showSnackbarWithMessage(messageResId)
    }

    override fun showProgress(isNeedToShow: Boolean) {
        progress_bar.visibility = if (isNeedToShow) View.VISIBLE else View.GONE
    }

    override fun hideFragment() {
        activity?.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(VIEW_STATE, initialViewState)
    }

    override fun onDestroy() {
        super.onDestroy()
        productPresenter.detach()
    }
}