package io.eprocurment.b2b2021.controllers;


import io.eprocurment.b2b2021.controllers.generics.CrudController;
import io.eprocurment.b2b2021.errors.ValidationErrorBuilder;
import io.eprocurment.b2b2021.models.users.User;
import io.eprocurment.b2b2021.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.security.Principal;

@RestController

@RequestMapping("users-roles/")

public class AuthController extends CrudController<User> {

    @Autowired
    private UserService userService;


    @PostConstruct
    private void postConstruct() {
        this.setService(userService);
    }


    @RequestMapping(value = "user-info/", method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }


    @PostMapping("/unlock/step-1/recovery-email")
    public ResponseEntity<?> createUser(@Valid @RequestBody String emailUser, Errors errors) {

        return new ResponseEntity<String>("'data", HttpStatus.OK);
    }

    @PostMapping("/singin")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, Errors errors) {
        this.userService.areOkayUsernameOrEmail(user);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);

    }
}
