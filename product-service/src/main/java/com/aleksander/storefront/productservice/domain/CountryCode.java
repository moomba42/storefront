package com.aleksander.storefront.productservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CountryCode {
    USA("United States"),
    GBR("United Kingdom"),
    POL("Poland"),
    DEU("Germany");

    private final String description;
}
