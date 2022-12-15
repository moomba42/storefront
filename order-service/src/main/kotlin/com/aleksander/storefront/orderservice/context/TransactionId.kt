package com.aleksander.storefront.orderservice.context

class TransactionId private constructor(val value: String) {
    companion object {
        @JvmStatic
        fun of(value: String): TransactionId {
            return TransactionId(value)
        }
    }
}