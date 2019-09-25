package com.artyomefimov.mystorage.database

import androidx.room.*
import com.artyomefimov.mystorage.model.Product

@Dao
interface ProductDao {
    @Query("select * from product order by name asc")
    fun findAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)
}