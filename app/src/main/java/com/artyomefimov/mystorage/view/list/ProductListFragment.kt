package com.artyomefimov.mystorage.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artyomefimov.mystorage.App
import com.artyomefimov.mystorage.R
import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.presenter.list.ProductListContract
import com.artyomefimov.mystorage.presenter.list.ProductListPresenter
import com.artyomefimov.mystorage.view.MainActivity
import com.artyomefimov.mystorage.view.detail.ProductDetailFragment
import com.artyomefimov.mystorage.view.utils.showSnackbarWithMessage
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_product_list.*

class ProductListFragment : Fragment(), ProductListContract.View {
    companion object {
        @JvmStatic
        fun newInstance() = ProductListFragment()
    }

    private lateinit var listPresenter: ProductListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPresenter = ProductListPresenter(App.repository(activity!!))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_product_list,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listPresenter.attach(this)

        fab_new_product.setOnClickListener {
            openProductFragmentFor(Product())
        }

        listPresenter.loadProducts()
    }

    override fun showProgress(isNeedToShow: Boolean) {
        progress_bar.visibility = if (isNeedToShow) View.VISIBLE else View.GONE
    }

    override fun showMessage(message: String) {
        showSnackbarWithMessage(message)
    }

    override fun showMessage(messageResId: Int) {
        showSnackbarWithMessage(messageResId)
    }

    override fun loadDataSuccess(products: List<Product>) {
        if (list_recycler_view.adapter == null) {
            list_recycler_view.adapter = ProductListAdapter(products,
                onItemClickAction = { product ->
                    openProductFragmentFor(product)
                })
        } else {
            (list_recycler_view.adapter as ProductListAdapter)
                .productList = products
        }
    }

    private fun openProductFragmentFor(product: Product) {
        if (this.activity != null) {
            val mainActivity = this.activity as MainActivity
            mainActivity.replaceFragment(ProductDetailFragment.newInstance(product))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listPresenter.detach()
    }
}