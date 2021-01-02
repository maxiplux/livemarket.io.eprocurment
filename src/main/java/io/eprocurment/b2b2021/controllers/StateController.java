package io.eprocurment.b2b2021.controllers;


import io.eprocurment.b2b2021.controllers.generics.CrudController;
import io.eprocurment.b2b2021.models.rules.State;
import io.eprocurment.b2b2021.repository.StateRepository;
import io.eprocurment.b2b2021.services.StateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1/states")
public class StateController extends CrudController<State> {
    @Autowired
    StateServices stateServices;


    @Autowired
    private StateRepository stateRepository;


    @PostConstruct
    private void postConstruct() {
        this.setService(stateServices);
    }


}
