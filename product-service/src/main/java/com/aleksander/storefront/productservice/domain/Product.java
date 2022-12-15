package com.aleksander.storefront.productservice.domain;

import com.aleksander.storefront.productservice.context.ImageId;
import com.aleksander.storefront.productservice.context.ProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {
    private ProductId id;

    private String developer;

    private String publisher;

    private LocalDate releaseDate;

    private Map<LanguageCode, StorefrontPage> localizedPages;

    private Map<CountryCode, Price> localizedPrices;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class StorefrontPage {
        private String title;

        private String description;

        private List<ImageId> galleryImageIds;

        private List<String> tags;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Price {
        private CurrencyCode currency;

        /**
         * This field expects amounts to be provided in a currency’s smallest unit.
         * For example, to charge 10 USD, provide an amount value of 1000 (that is, 1000 cents).
         * <p>
         * For zero-decimal currencies, still provide amounts as an integer but without multiplying by 100.
         * For example, to charge 500 JPY (Yen), provide an amount value of 500.
         */
        private long amount;
    }
}
