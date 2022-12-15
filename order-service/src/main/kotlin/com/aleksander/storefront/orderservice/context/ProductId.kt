package com.aleksander.storefront.orderservice.context

class ProductId private constructor(val value: String) {
    companion object {
        @JvmStatic
        fun of(value: String): ProductId {
            return ProductId(value)
        }
    }
}