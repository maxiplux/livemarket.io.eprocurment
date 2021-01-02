package io.eprocurment.b2b2021.controllers;


import io.eprocurment.b2b2021.controllers.generics.CrudController;
import io.eprocurment.b2b2021.models.products.Category;
import io.eprocurment.b2b2021.services.CategoryServices;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1/categories")
@Data
public class CategoryController extends CrudController<Category> {

    @Autowired
    CategoryServices categoryServices;

    @PostConstruct
    public void posContructor() {
        this.service = categoryServices;
    }

}
