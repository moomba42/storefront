package com.aleksander.storefront.productservice.infrastructure.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductMongoRepository extends MongoRepository<ProductDocument, String> {
}
