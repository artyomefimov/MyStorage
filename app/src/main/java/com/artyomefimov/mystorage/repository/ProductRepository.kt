package com.artyomefimov.mystorage.repository

import com.artyomefimov.mystorage.database.ProductDao
import com.artyomefimov.mystorage.model.Product
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductRepository(private val productDao: ProductDao) {

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

    fun delete(product: Product): Completable {
        return Completable.fromCallable {
            productDao.delete(product)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}