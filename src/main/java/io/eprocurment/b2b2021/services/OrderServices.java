package io.eprocurment.b2b2021.services;

import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.models.dtos.OrderDto;
import io.eprocurment.b2b2021.models.dtos.OrderDtoQuery;
import io.eprocurment.b2b2021.models.dtos.OrderLineDto;
import io.eprocurment.b2b2021.models.users.User;
import io.eprocurment.b2b2021.models.enums.StatesName;
import io.eprocurment.b2b2021.models.orders.Order;
import io.eprocurment.b2b2021.models.orders.OrderLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface OrderServices {

    Page<Order> findAll(Company company, OrderDtoQuery filter, Pageable pageable);

    Page<Order> findAll(Company company, Pageable pageable);


    Optional<Company> findCompanyByPrincipal(Principal principal);

    Optional<User> findCompanyByApproval(Principal principal);


    Order createOrder(Company company, OrderDto orderDto);

    Iterable<OrderLine> addItemToOrder(Company company, long orderId, OrderLineDto orderLineDto, Boolean isForUpdate);

    Iterable<OrderLine> addItemToOrder(Company company, long orderId, List<OrderLineDto> orderLineDto,Boolean isForUpdate);

    StatesName changeOrderStatus(Company company, long orderId, StatesName roleName);
}
