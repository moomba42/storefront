package com.aleksander.storefront.productservice.domain.service;

import com.aleksander.storefront.productservice.context.ProductId;
import com.aleksander.storefront.productservice.domain.Product;
import com.aleksander.storefront.productservice.domain.ProductBlueprint;

import java.util.List;

public interface ProductService {
    Product create(ProductBlueprint blueprint);

    List<Product> getAll();

    List<Product> getAllByIds(List<ProductId> ids);

}
