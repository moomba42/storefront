package com.aleksander.storefront.productservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CurrencyCode {
    EUR("Euro Member Countries"),
    GBP("United Kingdom Pound"),
    PLN("Poland Zloty"),
    USD("United States Dollar");

    private final String description;
}