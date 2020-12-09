package org.example.work;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "https://products-service")
public interface ProductProxyFeign {

    @GetMapping("/products")
    List<Product> findAllProducts();

    @GetMapping("/products/{id}")
    Product findProduct(@PathVariable("id") Long id);
}
