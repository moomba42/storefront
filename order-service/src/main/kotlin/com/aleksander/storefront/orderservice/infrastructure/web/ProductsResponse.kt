package com.aleksander.storefront.orderservice.infrastructure.web

data class ProductsResponse(
    val products: List<Product>
) {
    data class Product(
        val id: String,
        val localizedPrices: Map<String, Price>,
    )

    data class Price(
        val currency: String,
        val amount: Long,
    )
}