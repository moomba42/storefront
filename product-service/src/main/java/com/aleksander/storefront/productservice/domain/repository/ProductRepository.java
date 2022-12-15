package com.aleksander.storefront.productservice.domain.repository;

import com.aleksander.storefront.productservice.context.ProductId;
import com.aleksander.storefront.productservice.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product save(Product product);

    List<Product> getAll();
    List<Product> getAllByIds(List<ProductId> ids);
}
