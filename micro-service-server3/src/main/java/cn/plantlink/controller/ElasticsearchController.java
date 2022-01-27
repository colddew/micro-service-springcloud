package cn.plantlink.controller;

import cn.plantlink.pojo.Info;
import cn.plantlink.pojo.Product;
import cn.plantlink.service.ElasticsearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author colddew
 * @Date 2022-01-16
 */
@RestController
public class ElasticsearchController {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchController.class);

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

    @GetMapping("/info")
    public List<Info> getInfoList() throws Exception {
        return elasticsearchService.getInfoList();
    }

    @GetMapping("/info/{infoId}")
    public Info getInfo(@PathVariable("infoId") String infoId) throws Exception {
        return elasticsearchService.getInfo(infoId);
    }

    @PostMapping("/info")
    public String addInfo() throws Exception {
        return elasticsearchService.addInfo();
    }

    @PutMapping("/info/{infoId}")
    public String modifyInfo(@PathVariable("infoId") String infoId) throws Exception {
        return elasticsearchService.modifyInfo();
    }

    @DeleteMapping("/info/{infoId}")
    public String deleteInfo(@PathVariable("infoId") String infoId) throws Exception {
        return elasticsearchService.deleteInfo(infoId);
    }
}
