package com.aleksander.storefront.orderservice.context

class OrderNumber private constructor(val value: String) {
    companion object {
        @JvmStatic
        fun of(value: String): OrderNumber {
            return OrderNumber(value)
        }
    }
}