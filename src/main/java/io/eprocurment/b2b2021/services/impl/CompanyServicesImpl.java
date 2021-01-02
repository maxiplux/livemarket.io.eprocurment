package io.eprocurment.b2b2021.services.impl;


import io.eprocurment.b2b2021.exceptions.ResourceNotFoundException;
import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.repository.CompanyRepository;
import io.eprocurment.b2b2021.services.CompanyServices;
import io.eprocurment.b2b2021.services.impl.generics.CrudServicesImpl;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class CompanyServicesImpl extends CrudServicesImpl<Company> implements CompanyServices<Company> {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    @Qualifier(value = "dozer")
    private Mapper mapperDozer;


    @PostConstruct
    private void postConstruct() {

        this.repository = companyRepository;
    }


    public Optional<Company> UpdateById(long id, Company source) {

        Company company = this.companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));


        mapperDozer.map(source, company);
        this.companyRepository.save(company);

        return Optional.of(company);

    }

    @Override
    public Page<Company> findAll(Pageable page, Company filter) {
        return null;
    }


}
