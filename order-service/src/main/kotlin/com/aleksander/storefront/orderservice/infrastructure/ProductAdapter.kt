package com.aleksander.storefront.orderservice.infrastructure

import com.aleksander.storefront.orderservice.context.ProductId
import com.aleksander.storefront.orderservice.domain.CountryCode
import com.aleksander.storefront.orderservice.domain.CurrencyCode
import com.aleksander.storefront.orderservice.domain.Product
import com.aleksander.storefront.orderservice.domain.repository.ProductRepository
import com.aleksander.storefront.orderservice.infrastructure.web.ProductsResponse
import com.aleksander.storefront.orderservice.infrastructure.web.ProductRestService
import org.springframework.stereotype.Service

@Service
class ProductAdapter(
    private val productService: ProductRestService,
) : ProductRepository {
    override fun getAllByIdsAndCountry(ids: List<ProductId>, country: CountryCode): List<Product> {
        return productService.getAllByIds(
            ids = ids.map { it.value }
        ).thenApply { response ->
            response.products.map { it.toDomain(country) }
        }.get()
    }

    private fun ProductsResponse.Product.toDomain(country: CountryCode): Product {
        val price = this.localizedPrices[country.name] ?: throw Exception("No pricing available for the given country")
        return Product(
            id = ProductId.of(this.id),
            country = country,
            currency = CurrencyCode.valueOf(price.currency),
            priceAmount = price.amount,
        )
    }
}