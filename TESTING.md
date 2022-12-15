## Requirements
1. Make sure you have followed the instructions in the [RUNNING](RUNNING.md) file and you have started the service using `docker-compose`.
2. Chrome
3. curl

## Obtaining a keycloak session id
1. Head over to `http://localhost:8181/` using Chrome
2. You will be redirected to the keycloak login page
3. Type in `"test"` as both the username and password
4. Submit
5. You will be redirected back to `http://localhost:8181/` and you will see a whitelabel error page
6. Open the developer tools
    - Right click on the page and select `"Inspect"`
    - Or, select `"View"` from the Chrome toolbar and then `"Developer"`>`"Developer Tools"`
7. Select the `"Application"` tab in the developer tools toolbar
8. From the left side panel, select `"Storage"`>`"Cookies"`>`"http://localhost:8181"`
9. Copy and store the `"SESSION"` cookie value, which is the **keycloak session id**.

## Creating a product
Execute the following curl command, replacing **YOUR_SESSION_ID_COOKIE** with the keycloak session id you obtained in the first step.
```
curl -X POST --location "http://localhost:8181/api/v1/product" \
    -H "Content-Type: application/json" \
    -H "Cookie: SESSION=YOUR_SESSION_ID_COOKIE" \
    -d "{
          \"developer\": \"Aleksander Długosz\",
          \"publisher\": \"Aleksander Długosz\",
          \"releaseDate\": \"2022-10-10\",
          \"localizedPages\": {
            \"en_US\": {
              \"title\": \"Fiend Slayer\",
              \"description\": \"Slay fiends in this co-op adventure game.\",
              \"galleryImageIds\": [],
              \"tags\": [\"co-op\", \"hack&slash\"]
            }
          },
          \"localizedPrices\": {
            \"USA\": {
              \"currency\": \"USD\",
              \"amount\": 2900
            }
          }
        }"
```
Note down the `"id"` field value in the response. It will be used to place an order later.

## Fetching all products
Execute the following curl command, replacing **YOUR_SESSION_ID_COOKIE** with the keycloak session id you obtained in the first step.
```
curl -X GET --location "http://localhost:8181/api/v1/product" \
    -H "Cookie: SESSION=YOUR_SESSION_ID_COOKIE"
```

## Placing an order
Execute the following curl command, replacing **YOUR_SESSION_ID_COOKIE** with the keycloak session id you obtained in the first step, and **YOUR_PRODUCT_ID** with the product id you noted down when creating a product.
```
curl -X POST --location "http://localhost:8181/api/v1/order" \
    -H "Content-Type: application/json" \
    -H "Cookie: SESSION=YOUR_SESSION_ID_COOKIE" \
    -d "{
          \"productIds\": [\"YOUR_PRODUCT_ID\"]
        }"
```

The result is the order number.

## Visualising request traces
To open a visualization of request traces, head over to Zipkin: [http://localhost:9411/zipkin/](http://localhost:9411/zipkin/). You will need to be logged in. Type in `"test"` as both the username and password if asked by keycloak.

Here you will be able to run queries against the Zipkin database to see each request's information. Clicking on the `"RUN QUERY"` button without inputting anything will display all requests.

Clicking on the `"DEPENDENCIES"` tab will show a 2d graph of the infrastructure according to Zipkin.

## Checking microservices status
To open a dashboard of the discoverability status of the services, head over to Eureka: [http://localhost:8181/eureka/web](http://localhost:8181/eureka/web). You will need to be logged in. Type in `"test"` as both the username and password if asked by keycloak.
