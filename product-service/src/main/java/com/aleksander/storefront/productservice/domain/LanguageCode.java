package com.aleksander.storefront.productservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LanguageCode {
    en_US("English (United States)"),
    en_GB("English (United Kingdom)"),
    pl("Polish"),
    de("German (Standard)");

    private final String description;
}
