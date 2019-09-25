package com.artyomefimov.mystorage

import android.app.Application
import android.content.Context
import com.artyomefimov.mystorage.database.ProductDatabase
import com.artyomefimov.mystorage.repository.ProductRepository

class App: Application() {
    lateinit var productRepository: ProductRepository
    companion object {
        @JvmStatic
        fun repository(context: Context) =
            (context.applicationContext as App).productRepository
    }

    override fun onCreate() {
        super.onCreate()

        val database = ProductDatabase.getDatabase(this)
        productRepository = ProductRepository(database.productDao())
    }
}