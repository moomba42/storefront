package com.aleksander.storefront.orderservice.context

class UserId private constructor(val value: String) {
    companion object {
        @JvmStatic
        fun of(value: String): UserId {
            return UserId(value)
        }
    }
}