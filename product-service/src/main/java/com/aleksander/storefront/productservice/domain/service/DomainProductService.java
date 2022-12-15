package com.aleksander.storefront.productservice.domain.service;

import com.aleksander.storefront.productservice.context.ProductId;
import com.aleksander.storefront.productservice.domain.Product;
import com.aleksander.storefront.productservice.domain.ProductBlueprint;
import com.aleksander.storefront.productservice.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DomainProductService implements ProductService {

    private final ProductRepository repository;

    @Override
    public Product create(ProductBlueprint blueprint) {
        Product unsavedProduct = Product.builder()
                .developer(blueprint.getDeveloper())
                .publisher(blueprint.getPublisher())
                .releaseDate(blueprint.getReleaseDate())
                .localizedPages(blueprint.getLocalizedPages())
                .localizedPrices(blueprint.getLocalizedPrices())
                .build();

        Product savedProduct = repository.save(unsavedProduct);
        log.info("Product<{}> has been successfully created.", savedProduct.getId().getValue());

        return savedProduct;
    }

    @Override
    public List<Product> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Product> getAllByIds(List<ProductId> ids) {
        return repository.getAllByIds(ids);
    }
}
