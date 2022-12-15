package com.aleksander.storefront.orderservice.domain

import com.aleksander.storefront.orderservice.context.*
import java.time.LocalDateTime

data class Order(
        val id: OrderId?,
        val number: OrderNumber,
        val buyerId: UserId,
        val country: CountryCode,
        val currency: CurrencyCode,
        val items: List<OrderLine>,
        val creationDate: LocalDateTime,
) {
    data class OrderLine (
            val id: OrderLineId?,
            val productId: ProductId,
            val price: Long
    )
}