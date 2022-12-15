package com.aleksander.storefront.productservice.infrastructure.repository;

import com.aleksander.storefront.productservice.context.ImageId;
import com.aleksander.storefront.productservice.context.ProductId;
import com.aleksander.storefront.productservice.domain.CountryCode;
import com.aleksander.storefront.productservice.domain.CurrencyCode;
import com.aleksander.storefront.productservice.domain.LanguageCode;
import com.aleksander.storefront.productservice.domain.Product;
import com.aleksander.storefront.productservice.domain.repository.ProductRepository;
import com.aleksander.storefront.productservice.infrastructure.repository.mongo.ProductDocument;
import com.aleksander.storefront.productservice.infrastructure.repository.mongo.ProductMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductAdapter implements ProductRepository {

    private final ProductMongoRepository mongoRepository;

    @Override
    public Product save(Product product) {
        ProductDocument unsavedDocument = toDocument(product);
        ProductDocument savedDocument = mongoRepository.save(unsavedDocument);
        return toDomain(savedDocument);
    }

    @Override
    public List<Product> getAll() {
        return mongoRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Product> getAllByIds(List<ProductId> ids) {
        List<String> idsAsStrings = ids.stream().map(ProductId::getValue).toList();
        return StreamSupport.stream(mongoRepository.findAllById(idsAsStrings).spliterator(), false).map(this::toDomain).toList();
    }

    ProductDocument toDocument(Product product) {
        return ProductDocument.builder()
                .id(product.getId() == null ? null : product.getId().getValue())
                .developer(product.getDeveloper())
                .publisher(product.getPublisher())
                .releaseDate(Date.from(product.getReleaseDate().atStartOfDay().toInstant(ZoneOffset.UTC)))
                .localizedPages(product.getLocalizedPages().entrySet().stream().collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> toDocument(entry.getValue())
                )))
                .localizedPrices(product.getLocalizedPrices().entrySet().stream().collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> toDocument(entry.getValue())
                )))
                .build();
    }

    ProductDocument.StorefrontPageDocument toDocument(Product.StorefrontPage storefrontPage) {
        return ProductDocument.StorefrontPageDocument.builder()
                .title(storefrontPage.getTitle())
                .description(storefrontPage.getDescription())
                .galleryImageIds(storefrontPage.getGalleryImageIds().stream().map(ImageId::getValue).toList())
                .tags(storefrontPage.getTags())
                .build();
    }

    ProductDocument.PriceDocument toDocument(Product.Price price) {
        return ProductDocument.PriceDocument.builder()
                .currency(price.getCurrency().name())
                .amount(price.getAmount())
                .build();
    }

    Product toDomain(ProductDocument document) {
        return Product.builder()
                .id(ProductId.of(document.getId()))
                .developer(document.getDeveloper())
                .publisher(document.getPublisher())
                .releaseDate(document.getReleaseDate().toInstant().atZone(ZoneOffset.UTC).toLocalDate())
                .localizedPages(document.getLocalizedPages().entrySet().stream().collect(Collectors.toMap(
                        entry -> LanguageCode.valueOf(entry.getKey()),
                        entry -> toDomain(entry.getValue())
                )))
                .localizedPrices(document.getLocalizedPrices().entrySet().stream().collect(Collectors.toMap(
                        entry -> CountryCode.valueOf(entry.getKey()),
                        entry -> toDomain(entry.getValue())
                )))
                .build();
    }

    Product.StorefrontPage toDomain(ProductDocument.StorefrontPageDocument document) {
        return Product.StorefrontPage.builder()
                .title(document.getTitle())
                .description(document.getDescription())
                .galleryImageIds(document.getGalleryImageIds().stream().map(ImageId::of).toList())
                .tags(document.getTags())
                .build();
    }

    Product.Price toDomain(ProductDocument.PriceDocument document) {
        return Product.Price.builder()
                .currency(CurrencyCode.valueOf(document.getCurrency()))
                .amount(document.getAmount())
                .build();
    }
}
