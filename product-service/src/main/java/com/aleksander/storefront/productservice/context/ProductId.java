package com.aleksander.storefront.productservice.context;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class ProductId {
    private final String value;

    private ProductId(String value) {
        this.value = value;
    }

    public static ProductId of(String value) {
        return new ProductId(value);
    }
}
