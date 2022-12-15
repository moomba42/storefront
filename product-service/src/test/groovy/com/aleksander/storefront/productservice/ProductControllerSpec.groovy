package com.aleksander.storefront.productservice

import com.aleksander.storefront.productservice.infrastructure.repository.mongo.ProductMongoRepository
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerSpec extends Specification {
    @Shared
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.3")

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ProductMongoRepository repository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start()
        registry.add("spring.data.mongodb.uri", mongoDBContainer.&getReplicaSetUrl)
    }

    void cleanup() {
        repository.deleteAll();
    }

    void cleanupSpec() {
        mongoDBContainer.stop()
    }

    def "should create a product"() {
        given:
        Map request = [
                "developer"      : "Test developer",
                "publisher"      : "Test publisher",
                "releaseDate"    : "2022-10-10",
                "localizedPages" : [
                        "en_US": [
                                "title"          : "Test title",
                                "description"    : "Test description",
                                "galleryImageIds": ["a100", "b200"],
                                "tags"           : ["tag1", "tag2"]
                        ]
                ],
                "localizedPrices": [
                        "USA": [
                                "currency": "USD",
                                "amount"  : 2900
                        ]
                ]
        ]

        when:
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andReturn().response

        then:
        response.status == HttpStatus.OK.value()

        and:
        with(new JsonSlurper().parseText(response.contentAsString) as Map) {
            it.id in String
            it.developer == "Test developer"
            it.publisher == "Test publisher"
            it.releaseDate == "2022-10-10"
            it.localizedPages.en_US.title == "Test title"
            it.localizedPages.en_US.description == "Test description"
            it.localizedPages.en_US.galleryImageIds == ["a100", "b200"]
            it.localizedPages.en_US.tags == ["tag1", "tag2"]
            it.localizedPrices.USA.currency == "USD"
            it.localizedPrices.USA.amount == 2900
        }
    }

    def "should get all products"() {
        given:
        Map request = [
                "developer"      : "Test developer",
                "publisher"      : "Test publisher",
                "releaseDate"    : "2022-10-10",
                "localizedPages" : [
                        "en_US": [
                                "title"          : "Test title",
                                "description"    : "Test description",
                                "galleryImageIds": ["a100", "b200"],
                                "tags"           : ["tag1", "tag2"]
                        ]
                ],
                "localizedPrices": [
                        "USA": [
                                "currency": "USD",
                                "amount"  : 2900
                        ]
                ]
        ]

        and:
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))

        when:
        var response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/product"))
                .andReturn()
                .response

        then:
        response.status == HttpStatus.OK.value()

        and:
        with(new JsonSlurper().parseText(response.contentAsString) as Map) {
            it.products.size() == 2
        }
    }
}
