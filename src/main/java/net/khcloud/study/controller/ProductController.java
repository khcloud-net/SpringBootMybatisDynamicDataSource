package net.khcloud.study.controller;

import net.khcloud.study.exception.ProductNotFoundException;
import net.khcloud.study.dao.domain.Product;
import net.khcloud.study.dao.mapper.ProductMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by GBC on 2017/10/20.
 */
@Api
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/{id}")
    public Product getProductInfo(@PathVariable("id") Long productId) {
        return productMapper.select(productId);
    }

    @PutMapping("/{id}")
    public Product updateProductInfo(@PathVariable("id") Long productId, @RequestBody Product newProduct) {
        Product product = productMapper.select(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());
        productMapper.update(product);
        return product;
    }
}