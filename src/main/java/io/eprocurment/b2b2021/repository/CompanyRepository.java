package io.eprocurment.b2b2021.repository;


import io.eprocurment.b2b2021.models.corporate.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {


    Optional<Company> findByName(String name);

    Optional<Company> findByManager_Id(Long id);

    Optional<Company> findByManager_IdAndId(Long managerId, Long companyId);
}
