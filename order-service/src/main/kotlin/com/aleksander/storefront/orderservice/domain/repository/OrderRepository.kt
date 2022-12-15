package com.aleksander.storefront.orderservice.domain.repository

import com.aleksander.storefront.orderservice.domain.Order

interface OrderRepository {
    fun save(order: Order): Order
}