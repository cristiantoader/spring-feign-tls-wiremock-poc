package org.example.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsGateway {

    private final ProductProxyFeign productProxyFeign;

    @Autowired
    public ProductsGateway(ProductProxyFeign productProxyFeign) {
        this.productProxyFeign = productProxyFeign;
    }

    @GetMapping
    public List<Product> findAllProducts() {
        return productProxyFeign.findAllProducts();
    }

    @GetMapping("/{id}")
    public Product findProduct(@PathVariable("id") Long id) {
        return productProxyFeign.findProduct(id);
    }
}
