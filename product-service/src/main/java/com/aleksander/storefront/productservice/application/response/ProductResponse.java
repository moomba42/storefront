package com.aleksander.storefront.productservice.application.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponse {
    private String id;

    private String developer;

    private String publisher;

    private String releaseDate;

    private Map<String, StorefrontPage> localizedPages;

    private Map<String, Price> localizedPrices;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class StorefrontPage {
        private String title;

        private String description;

        private List<String> galleryImageIds;

        private List<String> tags;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Price {
        private String currency;

        private long amount;
    }
}
