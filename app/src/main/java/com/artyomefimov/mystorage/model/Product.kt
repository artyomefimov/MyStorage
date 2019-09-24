package com.artyomefimov.mystorage.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Product (
    @PrimaryKey var id: String = "",
    var name: String = "",
    var price: Double = 0.0,
    var imagePath: String = ""
): RealmObject(), Serializable