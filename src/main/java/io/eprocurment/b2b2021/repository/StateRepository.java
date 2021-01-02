package io.eprocurment.b2b2021.repository;


import io.eprocurment.b2b2021.models.rules.State;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StateRepository extends PagingAndSortingRepository<State, Long> {

    Optional<State> findById(Long id);

    Optional<State> findByName(String name);

}
