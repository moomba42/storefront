package com.aleksander.storefront.orderservice.domain

import com.aleksander.storefront.orderservice.context.ProductId

data class OrderPlacement(
        val productIds: List<ProductId>
)