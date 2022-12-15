package com.aleksander.storefront.orderservice.application

import com.aleksander.storefront.orderservice.application.request.PlaceOrderRequest
import com.aleksander.storefront.orderservice.context.ProductId
import com.aleksander.storefront.orderservice.domain.OrderPlacement
import com.aleksander.storefront.orderservice.domain.service.OrderService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/order")
class OrderController(
        val orderService: OrderService,
) {
    @PostMapping
    fun placeOrder(@RequestBody request: PlaceOrderRequest): ResponseEntity<Long> {
        val order = orderService.placeOrder(request.toDomain());
        return ResponseEntity.ok(order.id!!.value)
    }

    fun PlaceOrderRequest.toDomain(): OrderPlacement {
        return OrderPlacement(
                productIds = this.productIds.map { ProductId.of(it) }
        )
    }
}