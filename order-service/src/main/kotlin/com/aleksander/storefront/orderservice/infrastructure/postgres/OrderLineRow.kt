package com.aleksander.storefront.orderservice.infrastructure.postgres

import javax.persistence.*

@Entity
@Table(name = "t_order_line")
class OrderLineRow(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        val productId: String,
        val price: Long,
)