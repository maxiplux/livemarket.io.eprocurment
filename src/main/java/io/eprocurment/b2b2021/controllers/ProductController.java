package io.eprocurment.b2b2021.controllers;


import io.eprocurment.b2b2021.controllers.generics.CrudController;
import io.eprocurment.b2b2021.models.products.Product;
import io.eprocurment.b2b2021.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController extends CrudController<Product> {
//https://phoenixnap.com/kb/spring-boot-validation-for-rest-services


    @Autowired
    @Qualifier("ProductServices")
    private ProductService productService;

    @PostConstruct
    private void postConstruct() {
        this.setService(productService);
    }


}
