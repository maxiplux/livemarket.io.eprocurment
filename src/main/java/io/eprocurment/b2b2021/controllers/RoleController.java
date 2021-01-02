package io.eprocurment.b2b2021.controllers;


import io.eprocurment.b2b2021.controllers.generics.CrudController;
import io.eprocurment.b2b2021.models.users.Role;
import io.eprocurment.b2b2021.services.RoleServices;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1/roles")
@Data
public class RoleController extends CrudController<Role> {

    @Autowired
    private RoleServices roleServices;

    @PostConstruct
    public void posContructor() {
        this.service = roleServices;
    }

}
