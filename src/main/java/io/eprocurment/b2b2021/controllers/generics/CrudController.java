package io.eprocurment.b2b2021.controllers.generics;


import io.eprocurment.b2b2021.errors.ValidationErrorBuilder;
import io.eprocurment.b2b2021.exceptions.ResourceNotFoundException;
import io.eprocurment.b2b2021.services.impl.generics.CrudServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;


@Data

public abstract class CrudController<T> {

    protected CrudServices service;

    public CrudController() {
        super();

    }

    public CrudController(CrudServices service) {
        super();
        this.service = service;
    }


    @RequestMapping(value = "search/", method = RequestMethod.GET)
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> queryByPage(@RequestBody T filter, Pageable pageable) {

        Page<T> pageInfo = service.findAll(pageable, filter);
        if (pageInfo.getContent().isEmpty()) {
            return new ResponseEntity<Page<T>>(pageInfo, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<T>>(pageInfo, HttpStatus.OK);
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> queryByPage(Pageable pageable) {

        Page<T> pageInfo = service.findAll(pageable);
        if (pageInfo.getContent().isEmpty()) {
            return new ResponseEntity<Page<T>>(pageInfo, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Page<T>>(pageInfo, HttpStatus.OK);
    }
    @GetMapping(value = "{id}/")
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> show(@RequestParam("id") long id) {
        return new ResponseEntity<T>((T) service.show(id), HttpStatus.OK);
    }




    @DeleteMapping(value = "{id}/")
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable long id) {
        service.deleteById(id);
    }



    @PatchMapping("{id}/")
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateByIdAndEntity(@RequestParam("id") long id, @Valid @RequestBody T element, Errors errors) throws Throwable {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        this.service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + id));


        Optional<T> optionalProduct = this.service.UpdateById(id, element);
        if (optionalProduct.isPresent()) {
            return new ResponseEntity<T>(optionalProduct.get(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Without updates", HttpStatus.NO_CONTENT);
    }


    @PutMapping("{id}/")
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody T elementForUpdate, Errors errors) throws Throwable {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        this.service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + id));

        Optional<T> optionalProduct = service.UpdateById(id, elementForUpdate);
        if (optionalProduct.isPresent()) {
            return new ResponseEntity<T>(optionalProduct.get(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Without updates", HttpStatus.NO_CONTENT);
    }


    @PostMapping
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody T element, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        return new ResponseEntity<T>((T) service.create(element), HttpStatus.OK);

    }


}
