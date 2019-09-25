package com.artyomefimov.mystorage.repository

import androidx.lifecycle.MutableLiveData
import com.artyomefimov.mystorage.database.ProductDao
import com.artyomefimov.mystorage.model.Product
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductRepository (private val productDao: ProductDao) {

    fun findAll(): Single<List<Product>> {
        return Single.fromCallable {
            productDao.findAll()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun save(product: Product): Completable {
        return Completable.fromCallable {
            productDao.save(product)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun update(product: Product): Completable {
        return Completable.fromCallable {
            productDao.update(product)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun delete(product: Product): Completable {
        return Completable.fromCallable {
            productDao.delete(product)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}