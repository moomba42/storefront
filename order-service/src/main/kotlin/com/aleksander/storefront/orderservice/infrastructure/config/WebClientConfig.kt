package com.aleksander.storefront.orderservice.infrastructure.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Bean
    @LoadBalanced
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }

    @Bean
    fun productsServiceWebClient(webClientBuilder: WebClient.Builder): WebClient {
        return webClientBuilder.baseUrl("http://product-service").build()
    }
}