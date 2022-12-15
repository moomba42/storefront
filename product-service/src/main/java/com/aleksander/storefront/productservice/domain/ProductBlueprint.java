package com.aleksander.storefront.productservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductBlueprint {
    private String developer;

    private String publisher;

    private LocalDate releaseDate;

    private Map<LanguageCode, Product.StorefrontPage> localizedPages;

    private Map<CountryCode, Product.Price> localizedPrices;
}
