package io.eprocurment.b2b2021.controllers;

import io.eprocurment.b2b2021.models.dtos.OrderDto;
import io.eprocurment.b2b2021.models.dtos.OrderDtoQuery;
import io.eprocurment.b2b2021.models.dtos.OrderLineDto;
import io.eprocurment.b2b2021.models.enums.RoleName;
import io.eprocurment.b2b2021.models.enums.StatesName;
import io.eprocurment.b2b2021.models.orders.Order;
import io.eprocurment.b2b2021.models.orders.OrderLine;
import io.eprocurment.b2b2021.services.OrderServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Log4j2
public class OrderController {

    @Autowired
    private OrderServices orderServices;
    private Boolean forceCreate =false;


    @RequestMapping(value = "search/", method = RequestMethod.GET)
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @RolesAllowed({"ROLE_COMPANY_MANAGER", "ROLE_COMPANY_BRANCH_ASISTANCE"})
    public ResponseEntity<?> queryByPage(Principal principal, @RequestBody OrderDtoQuery filter, Pageable pageable) {

        var optionalCompany = this.orderServices.findCompanyByPrincipal(principal);
        if (optionalCompany.isPresent()) {

            Page<Order> pageInfo = orderServices.findAll(optionalCompany.get(), pageable);
            if (pageInfo.getContent().isEmpty()) {
                return new ResponseEntity<Page<Order>>(pageInfo, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Page<Order>>(pageInfo, HttpStatus.OK);
        }
        return new ResponseEntity<String>("No data Found", HttpStatus.NO_CONTENT);


    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @RolesAllowed({"ROLE_COMPANY_MANAGER", "ROLE_COMPANY_BRANCH_ASISTANCE"})
    public ResponseEntity<?> queryByPage(Principal principal, Pageable pageable) {

        var optionalCompany = this.orderServices.findCompanyByPrincipal(principal);

        if (optionalCompany.isPresent()) {
            var pageInfo = orderServices.findAll(optionalCompany.get(), pageable);

            if (pageInfo.getContent().isEmpty()) {
                return new ResponseEntity<Page<Order>>(pageInfo, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Page<Order>>(pageInfo, HttpStatus.OK);
        }

        return new ResponseEntity<String>("No data Found", HttpStatus.NO_CONTENT);

    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @RolesAllowed({"ROLE_COMPANY_MANAGER", "ROLE_COMPANY_BRANCH_ASISTANCE"})
    public ResponseEntity<?> createOrder(Principal principal,@RequestBody OrderDto orderDto) {

        var optionalCompany = this.orderServices.findCompanyByPrincipal(principal);

        if (optionalCompany.isPresent()) {
            var order = orderServices.createOrder(optionalCompany.get(), orderDto);
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        }

        log.warn("Failed trying to build Order", orderDto);
        return new ResponseEntity<String>("No data Found", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("{orderId}/line/")
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @RolesAllowed({"ROLE_COMPANY_MANAGER", "ROLE_COMPANY_BRANCH_ASISTANCE"})
    public ResponseEntity<?> addProductsToOrder(@PathVariable("orderId") long orderId, Principal principal, @RequestBody  OrderLineDto orderLineDto) {

        var optionalCompany = this.orderServices.findCompanyByPrincipal(principal);

        if (optionalCompany.isPresent()) {
            var dbOrderLine = orderServices.addItemToOrder(optionalCompany.get(),orderId, orderLineDto, forceCreate);
            return new ResponseEntity<Iterable<OrderLine>>(dbOrderLine, HttpStatus.OK);
        }

        log.warn("Failed trying to build Order", orderLineDto);
        return new ResponseEntity<String>("No data Found", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("{orderId}/line-batch/")
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @RolesAllowed({"ROLE_COMPANY_MANAGER", "ROLE_COMPANY_BRANCH_ASISTANCE"})
    public ResponseEntity<?> addProductsToOrderBatch(@PathVariable("orderId") long orderId, Principal principal, @RequestBody List<OrderLineDto> orderLineDtos) {

        var optionalCompany = this.orderServices.findCompanyByPrincipal(principal);

        if (optionalCompany.isPresent()) {
            var dbOrderLine = orderServices.addItemToOrder(optionalCompany.get(),orderId, orderLineDtos, forceCreate);
            return new ResponseEntity<Iterable<OrderLine>>(dbOrderLine, HttpStatus.OK);
        }

        log.warn("Failed trying to build Order Lines", orderLineDtos);
        return new ResponseEntity<String>("No data Found", HttpStatus.BAD_REQUEST);

    }

    @PatchMapping("{orderId}/change-status/")
    @Operation(security = {@SecurityRequirement(name = "Bearer")})
    @RolesAllowed({"ROLE_COMPANY_MANAGER"})
    public ResponseEntity<?> changeOrderStatus(@PathVariable("orderId") long orderId, Principal principal, StatesName statesName) {

        var optionalCompany = this.orderServices.findCompanyByPrincipal(principal);

        if (optionalCompany.isPresent()) {
            var resultRoleName = orderServices.changeOrderStatus(optionalCompany.get(),orderId, statesName);
            return new ResponseEntity<StatesName>(resultRoleName, HttpStatus.OK);
        }

        log.warn("Failed trying to build Order", statesName);
        return new ResponseEntity<String>("No data Found", HttpStatus.BAD_REQUEST);

    }


}
