package com.aleksander.storefront.orderservice.infrastructure.postgres

import javax.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="t_orders")
class OrderRow (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,
        val number: String,
        val buyerId: String,
        val country: String,
        val currency: String,
        @OneToMany(cascade = [CascadeType.ALL])
        val items: List<OrderLineRow>,
        val creationDate: LocalDateTime,
)