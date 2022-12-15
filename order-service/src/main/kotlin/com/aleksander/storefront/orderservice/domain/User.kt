package com.aleksander.storefront.orderservice.domain

import com.aleksander.storefront.orderservice.context.UserId

class User (
        val id: UserId,
        val country: CountryCode,
)