package cn.plantlink.controller;

import cn.plantlink.pojo.Product;
import cn.plantlink.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author colddew
 * @Date 2022-01-16
 */
@RestController
public class ElasticsearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @GetMapping("/product")
    public List<Product> getProductList() throws Exception {
        return elasticsearchService.getProductList();
    }

    @GetMapping("/product/{productId}")
    public Product getProduct(@PathVariable("productId") String productId) throws Exception {
        return elasticsearchService.getProduct(productId);
    }

    @PostMapping("/product")
    public String addProduct(@RequestBody Product product) throws Exception {
        return elasticsearchService.addProduct(product);
    }

    @PutMapping("/product/{productId}")
    public String modifyProduct(@PathVariable("productId") String productId, @RequestBody Product product) throws Exception {
        return elasticsearchService.modifyProduct(product, productId);
    }

    @DeleteMapping("/product/{productId}")
    public String deleteProduct(@PathVariable("productId") String productId) throws Exception {
        return elasticsearchService.deleteProduct(productId);
    }
}
