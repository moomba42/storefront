package com.aleksander.storefront.orderservice.context

class OrderId private constructor(val value: Long) {
    companion object {
        @JvmStatic
        fun of(value: Long): OrderId {
            return OrderId(value)
        }
    }
}