# Storefront

This is a barebones partial implementation of a online game store platform.\
The goal of this project was to present a variety of skills in creating microservices which include:
- Architecture design
- Documentation
- Testing
- Library usage
- External tool usage
- Fault tolerance
- Monitoring
- Streamlined building
- Maintainability

My priority was to present an example in each of those categories. Due to time constraints some functionalities like product drafts and authorization had to be left out, as i think its trival to add them later on, and they dont add to the main goal of showcasing my skills.

Below, I have listed an optimal way of reviewing this project:
1. [Figjam architecture overview](https://www.figma.com/file/VNXSeQI3kQ4FSLS5axzwkw/Storefront-Overview?node-id=0%3A1&t=qkIEObVbqtDC24Jp-1)
2. [How to run the project](RUNNING.md)
3. [How to use the API](TESTING.md)
4. Remaining TODOs are listed below

If there are any issues or you need permissions please contact me at olekdlugi@gmail.com


## Remaining TODOs
Here is a list of remaining things to do that have not been finished due to time constraints:
- Set up ElasticSearch to add intelligent search to the products and orders services
- Set up Kibana for advanced monitoring
- Set up Spring Cloud Config to externalize sensitive service configurations for different environments
- Pass principal information in the headers to allow services to use the gateway security context.
- Add role based authorization on the gateway
- Add product drafts to the products service
- Add the image storage service to host product image content
- Add the binary storage service to host product binary content
- Add angular frontend
- Add nginx instances to host the frontend
- Route Zipkin through the gateway as a protected route.
- Add a dev jib docker build for macos with aarch64 architecture (faster execution on macos) for development
- Add custom error responses using `@ControllerAdvice/@ExceptionHandler` instead of the standard spring boot 500 error message.
- Set up k8s
- Add postgres & mongodb replication
- Migrate from Sleuth to Micrometer once Micrometer reaches maturity, which will allow migration to Spring Boot 3.0.0

## Known issues
- On M1 macOS the docker environment might sometimes hang, and one or two containers might freeze. This might be due to the `arm64` arch of the docker images produced by the maven build explained in the [RUNNING](RUNNING.md) file. In such case restart the affected containers. This would be fixed if a second build configuration would be added just for m1 macos - which is possible.