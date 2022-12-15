package com.aleksander.storefront.productservice.context;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class ImageId {
    private final String value;

    private ImageId(String value) {
        this.value = value;
    }

    public static ImageId of(String value) {
        return new ImageId(value);
    }
}
