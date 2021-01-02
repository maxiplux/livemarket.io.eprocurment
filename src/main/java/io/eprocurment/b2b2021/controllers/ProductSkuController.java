package io.eprocurment.b2b2021.controllers;


import io.eprocurment.b2b2021.controllers.generics.CrudController;
import io.eprocurment.b2b2021.models.products.ProductSku;
import io.eprocurment.b2b2021.services.ProductService;
import io.eprocurment.b2b2021.services.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1/product/sku/")
public class ProductSkuController extends CrudController<ProductSku> {
//https://phoenixnap.com/kb/spring-boot-validation-for-rest-services


    @Autowired
    @Qualifier("ProductSku")
    private ProductSkuService productService;

    @PostConstruct
    private void postConstruct() {
        this.setService(productService);
    }


}
