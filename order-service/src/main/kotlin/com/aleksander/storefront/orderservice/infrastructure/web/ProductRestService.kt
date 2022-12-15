package com.aleksander.storefront.orderservice.infrastructure.web

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.sleuth.SpanName
import org.springframework.cloud.sleuth.Tracer
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.util.concurrent.CompletableFuture

@Service
class ProductRestService(
    @Qualifier("productsServiceWebClient")
    private val webClient: WebClient
) {
    @CircuitBreaker(name = PRODUCT_SERVICE_CIRCUIT_BREAKER_ID, fallbackMethod = "getAllByIdsFallback")
    @TimeLimiter(name = PRODUCT_SERVICE_TIME_LIMITER_ID)
    @Retry(name = PRODUCT_SERVICE_RETRY_ID)
    fun getAllByIds(ids: List<String>): CompletableFuture<ProductsResponse> {
        return webClient.get().uri {
            it.path("/api/v1/product")
                .queryParam("ids", ids)
                .build()
        }.retrieve()
            .bodyToMono(ProductsResponse::class.java)
            .toFuture()
    }

    fun getAllByIdsFallback(
        ids: List<String>,
        runtimeException: RuntimeException
    ): CompletableFuture<ProductsResponse> {
        return CompletableFuture.failedFuture(Exception("The product service is down"))
    }

    companion object {
        const val PRODUCT_SERVICE_CIRCUIT_BREAKER_ID = "productservice"
        const val PRODUCT_SERVICE_TIME_LIMITER_ID = "productservice"
        const val PRODUCT_SERVICE_RETRY_ID = "productservice"
    }
}