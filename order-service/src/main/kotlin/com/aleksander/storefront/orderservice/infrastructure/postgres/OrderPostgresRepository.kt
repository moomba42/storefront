package com.aleksander.storefront.orderservice.infrastructure.postgres

import org.springframework.data.jpa.repository.JpaRepository

interface OrderPostgresRepository : JpaRepository<OrderRow, Long>