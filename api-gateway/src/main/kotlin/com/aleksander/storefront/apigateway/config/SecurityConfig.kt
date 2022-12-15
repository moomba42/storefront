package com.aleksander.storefront.apigateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun keycloakLogoutSuccessHandler(repository: ReactiveClientRegistrationRepository): ServerLogoutSuccessHandler {
        val oidcLogoutSuccessHandler = OidcClientInitiatedServerLogoutSuccessHandler(repository)
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/login")

        return oidcLogoutSuccessHandler
    }

    @Bean
    fun springSecurityFilterChain(serverHttpSecurity: ServerHttpSecurity, logoutHandler: ServerLogoutSuccessHandler): SecurityWebFilterChain {
        serverHttpSecurity
            .csrf().disable()
            .authorizeExchange()
            .anyExchange()
            .authenticated()
            .and()
            .oauth2Login(Customizer.withDefaults())
            .logout()
            .logoutSuccessHandler(logoutHandler)
        return serverHttpSecurity.build()
    }
}