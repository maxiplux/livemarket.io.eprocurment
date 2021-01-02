package io.eprocurment.b2b2021.repository;


import io.eprocurment.b2b2021.models.orders.Order;
import io.eprocurment.b2b2021.models.orders.OrderLine;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderLineRepository extends PagingAndSortingRepository<OrderLine, Long> {

    Optional<OrderLine> findById(Long id);

    List<OrderLine> findByOrderHeader(Order orderHeader);

}
