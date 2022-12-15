package com.aleksander.storefront.orderservice.domain.service

import com.aleksander.storefront.orderservice.context.OrderNumber
import com.aleksander.storefront.orderservice.context.UserId
import com.aleksander.storefront.orderservice.domain.*
import com.aleksander.storefront.orderservice.domain.repository.OrderRepository
import com.aleksander.storefront.orderservice.domain.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class DomainOrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) : OrderService {

    @Transactional
    override fun placeOrder(orderPlacement: OrderPlacement): Order {
        // Fetch user details with country of origin
        // Fetch product details using country of origin
        // Construct an order and save it
        // Return the order

        val country = CountryCode.USA
        val currency = country.toCurrencyCode()

        val products = productRepository.getAllByIdsAndCountry(orderPlacement.productIds, country)

        val allProductsMatchCurrency = products.all { it.currency == currency }
        if (!allProductsMatchCurrency) {
            throw Exception("Some products contain invalid pricing information")
        }

        return orderRepository.save(
            Order(
                id = null,
                number = OrderNumber.of(UUID.randomUUID().toString()),
                buyerId = UserId.of(""),
                country = country,
                currency = currency,
                items = products.map { it.toOrderLine() },
                creationDate = LocalDateTime.now(),
            )
        )
    }

    private fun CountryCode.toCurrencyCode(): CurrencyCode {
        return when (this) {
            CountryCode.USA -> CurrencyCode.USD
            CountryCode.GBR -> CurrencyCode.GBP
            CountryCode.POL -> CurrencyCode.PLN
            CountryCode.DEU -> CurrencyCode.EUR
        }
    }

    private fun Product.toOrderLine(): Order.OrderLine {
        return Order.OrderLine(
            id = null,
            productId = this.id,
            price = this.priceAmount
        )
    }
}