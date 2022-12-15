package com.aleksander.storefront.productservice.application.rest;

import com.aleksander.storefront.productservice.application.request.CreateProductRequest;
import com.aleksander.storefront.productservice.application.response.ProductResponse;
import com.aleksander.storefront.productservice.application.response.ProductsResponse;
import com.aleksander.storefront.productservice.context.ImageId;
import com.aleksander.storefront.productservice.context.ProductId;
import com.aleksander.storefront.productservice.domain.*;
import com.aleksander.storefront.productservice.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    final static DateTimeFormatter RELEASE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody CreateProductRequest request) {
        ProductBlueprint blueprint = toDomain(request);
        Product product = productService.create(blueprint);
        ProductResponse response = toResponse(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> getAll(@RequestParam(value = "ids", required = false) List<String> ids) {
        List<ProductResponse> products;
        if(ids == null || ids.size() == 0) {
            products = productService.getAll().stream().map(this::toResponse).toList();
        } else {
            List<ProductId> domainIds = ids.stream().map(ProductId::of).toList();
            products = productService.getAllByIds(domainIds).stream().map(this::toResponse).toList();
        }

        return ResponseEntity.ok(ProductsResponse.builder().products(products).build());
    }

    ProductBlueprint toDomain(CreateProductRequest request) {
        return ProductBlueprint.builder()
                .developer(request.getDeveloper())
                .publisher(request.getPublisher())
                .releaseDate(LocalDate.parse(request.getReleaseDate(), RELEASE_DATE_FORMAT))
                .localizedPages(request.getLocalizedPages().entrySet().stream().collect(Collectors.toMap(
                        entry -> LanguageCode.valueOf(entry.getKey()),
                        entry -> toDomain(entry.getValue())
                )))
                .localizedPrices(request.getLocalizedPrices().entrySet().stream().collect(Collectors.toMap(
                        entry -> CountryCode.valueOf(entry.getKey()),
                        entry -> toDomain(entry.getValue())
                )))
                .build();
    }

    Product.StorefrontPage toDomain(CreateProductRequest.StorefrontPage request) {
        return Product.StorefrontPage.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .galleryImageIds(request.getGalleryImageIds().stream().map(ImageId::of).toList())
                .tags(request.getTags())
                .build();
    }

    Product.Price toDomain(CreateProductRequest.Price request) {
        return Product.Price.builder()
                .currency(CurrencyCode.valueOf(request.getCurrency()))
                .amount(request.getAmount())
                .build();
    }

    ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId().getValue())
                .developer(product.getDeveloper())
                .publisher(product.getPublisher())
                .releaseDate(product.getReleaseDate().format(RELEASE_DATE_FORMAT))
                .localizedPages(product.getLocalizedPages().entrySet().stream().collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> toResponse(entry.getValue())
                )))
                .localizedPrices(product.getLocalizedPrices().entrySet().stream().collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> toResponse(entry.getValue())
                )))
                .build();
    }

    ProductResponse.StorefrontPage toResponse(Product.StorefrontPage storefrontPage) {
        return ProductResponse.StorefrontPage.builder()
                .title(storefrontPage.getTitle())
                .description(storefrontPage.getDescription())
                .galleryImageIds(storefrontPage.getGalleryImageIds().stream().map(ImageId::getValue).toList())
                .tags(storefrontPage.getTags())
                .build();
    }

    ProductResponse.Price toResponse(Product.Price price) {
        return ProductResponse.Price.builder()
                .currency(price.getCurrency().name())
                .amount(price.getAmount())
                .build();
    }
}
