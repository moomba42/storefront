package com.aleksander.storefront.orderservice.domain.service

import com.aleksander.storefront.orderservice.domain.Order
import com.aleksander.storefront.orderservice.domain.OrderPlacement

interface OrderService {
    fun placeOrder(orderPlacement: OrderPlacement): Order
}