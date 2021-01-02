package io.eprocurment.b2b2021.controllers;


import io.eprocurment.b2b2021.controllers.generics.CrudController;
import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.services.CompanyServices;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1/companay")
@Data
public class CompanyController extends CrudController<Company> {

    @Autowired
    CompanyServices companyServices;

    @PostConstruct
    public void posContructor() {
        this.service = companyServices;
    }

}
