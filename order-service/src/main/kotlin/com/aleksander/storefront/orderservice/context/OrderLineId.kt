package com.aleksander.storefront.orderservice.context

class OrderLineId private constructor(val value: Long) {
    companion object {
        @JvmStatic
        fun of(value: Long): OrderLineId {
            return OrderLineId(value)
        }
    }
}