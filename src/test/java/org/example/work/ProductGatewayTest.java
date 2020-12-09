package org.example.work;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.Arrays.asList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FeignTlsApplication.class})
@Slf4j
public class ProductGatewayTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().httpsPort(8443));

    @Autowired
    private ProductsGateway productsGateway;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAllProducts() throws JsonProcessingException {
        List<Product> mockResponse = asList(new Product(1l, "name", 23.00, "customerId"));

        wireMockRule.stubFor(get(urlEqualTo("/products"))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                .withBody(objectMapper.writeValueAsString(mockResponse))));

        List<Product> actualResponse = productsGateway.findAllProducts();
        assertEquals(mockResponse.size(), actualResponse.size());
        assertEquals(mockResponse.get(0), actualResponse.get(0));
    }

    @Test
    public void testProductById() throws JsonProcessingException {
        Product mockResponse = new Product(1l, "name", 23.00, "customerId");

        wireMockRule.stubFor(get(urlEqualTo("/products/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(mockResponse))));

        Product actualResponse = productsGateway.findProduct(mockResponse.getId());
        assertEquals(mockResponse, actualResponse);
    }
}
