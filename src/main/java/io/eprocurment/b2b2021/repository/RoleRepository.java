package io.eprocurment.b2b2021.repository;


import io.eprocurment.b2b2021.models.users.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    List<Role> findAll();
}
