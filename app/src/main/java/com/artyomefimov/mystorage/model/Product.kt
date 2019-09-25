package com.artyomefimov.mystorage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "product")
class Product (
    @PrimaryKey @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "price") var price: Double = 0.0,
    @ColumnInfo(name = "imagePath") var imagePath: String = ""
): Serializable