package io.eprocurment.b2b2021.repository;


import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.models.orders.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Optional<Order> findById(Long id);

    Optional<Order> findByIdAndAndCompany(Long id,Company company);


    Page<Order> findAllByCompany(Company company, Pageable pageable);
}
