package com.aleksander.storefront.orderservice.infrastructure

import com.aleksander.storefront.orderservice.context.*
import com.aleksander.storefront.orderservice.domain.CountryCode
import com.aleksander.storefront.orderservice.domain.CurrencyCode
import com.aleksander.storefront.orderservice.domain.Order
import com.aleksander.storefront.orderservice.domain.repository.OrderRepository
import com.aleksander.storefront.orderservice.infrastructure.postgres.OrderLineRow
import com.aleksander.storefront.orderservice.infrastructure.postgres.OrderPostgresRepository
import com.aleksander.storefront.orderservice.infrastructure.postgres.OrderRow
import org.springframework.stereotype.Service

@Service
class OrderAdapter(
    private val postgresRepository: OrderPostgresRepository
) : OrderRepository {
    override fun save(order: Order): Order {
        return postgresRepository.save(order.toRow()).toDomain()
    }

    private fun Order.toRow(): OrderRow {
        return OrderRow(
            id = this.id?.value,
            number = this.number.value,
            buyerId = this.buyerId.value,
            country = this.country.name,
            currency = this.currency.name,
            items = this.items.map { it.toRow() },
            creationDate = this.creationDate,
        )
    }

    private fun Order.OrderLine.toRow(): OrderLineRow {
        return OrderLineRow(
            id = this.id?.value,
            productId = this.productId.value,
            price = this.price,
        )
    }

    private fun OrderRow.toDomain(): Order {
        return Order(
            id = OrderId.of(this.id!!),
            number = OrderNumber.of(this.number),
            buyerId = UserId.of(this.buyerId),
            country = CountryCode.valueOf(this.country),
            currency = CurrencyCode.valueOf(this.currency),
            items = this.items.map { it.toDomain() },
            creationDate = this.creationDate,
        )
    }

    private fun OrderLineRow.toDomain(): Order.OrderLine {
        return Order.OrderLine(
            id = OrderLineId.of(this.id!!),
            productId = ProductId.of(this.productId),
            price = this.price,
        )
    }
}