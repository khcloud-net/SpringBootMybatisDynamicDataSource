package net.khcloud.study.controller;

import net.khcloud.study.exception.ProductNotFoundException;
import net.khcloud.study.dao.domain.Product;
import net.khcloud.study.dao.mapper.ProductMapper;
import io.swagger.annotations.Api;
import net.khcloud.study.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SYF on 2023/01/16.
 */
@Api
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Product getProductInfo(@PathVariable("id") Long productId) {
        return productService.getProduct(productId);
    }

    @PutMapping("")
    public Product updateProductInfo(@RequestBody Product product) {
        return productService.updateProduct(product);
    }
}