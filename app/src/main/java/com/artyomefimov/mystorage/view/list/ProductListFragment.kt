package com.artyomefimov.mystorage.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artyomefimov.mystorage.R
import com.artyomefimov.mystorage.model.Product
import com.artyomefimov.mystorage.presenter.list.ProductListContract
import com.artyomefimov.mystorage.presenter.list.ProductListPresenter
import com.artyomefimov.mystorage.view.MainActivity
import com.artyomefimov.mystorage.view.detail.ProductDetailFragment
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_product_list.*
import java.util.*

class ProductListFragment(
    private val listPresenter: ProductListPresenter = ProductListPresenter()
) : Fragment(), ProductListContract.View {
    companion object {
        @JvmStatic
        fun newInstance() = ProductListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_product_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listPresenter.attach(this)

        fab_new_product.setOnClickListener {
            //openProductFragmentFor(Product())

            // todo for test purpose
            var product = Product()
            Realm.getDefaultInstance().executeTransaction {
                product = it.copyFromRealm(it.createObject(Product::class.java, UUID.randomUUID().toString()))
                product.name = "name"
                product.price = 21.0
            }
            openProductFragmentFor(product)
        }

        listPresenter.loadProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listPresenter.detach()
    }

    override fun showProgress(isNeedToShow: Boolean) {
        progress_bar.visibility = if (isNeedToShow) View.VISIBLE else View.GONE
    }

    override fun showErrorMessage(message: String) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun loadDataSuccess(products: RealmResults<Product>) {
        list_recycler_view.adapter = ProductListAdapter(products,
            onItemClickAction = { product ->
                openProductFragmentFor(product)
            })
    }

    private fun openProductFragmentFor(product: Product) {
        if (this.activity != null) {
            val mainActivity = this.activity as MainActivity
            mainActivity.replaceFragment(ProductDetailFragment.newInstance(product))
        }
    }
}