package com.aleksander.storefront.productservice.infrastructure.repository.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDocument {
    @Id
    private String id;

    private String developer;

    private String publisher;

    private Date releaseDate;

    private Map<String, StorefrontPageDocument> localizedPages;

    private Map<String, PriceDocument> localizedPrices;


    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class StorefrontPageDocument {
        private String title;

        private String description;

        private List<String> galleryImageIds;

        private List<String> tags;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class PriceDocument {
        private String currency;

        private long amount;
    }
}
