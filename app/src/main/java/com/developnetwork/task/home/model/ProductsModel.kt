package com.developnetwork.task.home.model

data class ProductsModel(
    val limit: Int,
    val products: ArrayList<ProductItem>,
    val skip: Int,
    val total: Int
)

data class ProductItem(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: ArrayList<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
)