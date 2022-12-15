package com.aleksander.storefront.orderservice.domain.repository

import com.aleksander.storefront.orderservice.context.ProductId
import com.aleksander.storefront.orderservice.domain.CountryCode
import com.aleksander.storefront.orderservice.domain.CurrencyCode
import com.aleksander.storefront.orderservice.domain.Product

interface ProductRepository {
    fun getAllByIdsAndCountry(ids: List<ProductId>, country: CountryCode): List<Product>
}