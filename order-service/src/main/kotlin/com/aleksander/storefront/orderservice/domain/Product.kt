package com.aleksander.storefront.orderservice.domain

import com.aleksander.storefront.orderservice.context.ProductId

data class Product(
        val id: ProductId,
        val country: CountryCode,
        val currency: CurrencyCode,
        val priceAmount: Long,
)