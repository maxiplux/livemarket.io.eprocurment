package io.eprocurment.b2b2021.services.impl;


import io.eprocurment.b2b2021.models.users.Role;
import io.eprocurment.b2b2021.repository.RoleRepository;
import io.eprocurment.b2b2021.services.RoleServices;
import io.eprocurment.b2b2021.services.impl.generics.CrudServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class RoleServicesImpl extends CrudServicesImpl<Role> implements RoleServices<Role> {

    @Autowired
    private RoleRepository roleRepository;


    @PostConstruct
    public void posContructor() {
        this.setRepository(roleRepository);
    }


    public Optional<Role> UpdateById(long id, Role element) {
        Optional<Role> optionalCurrentCompany = this.repository.findById(id);
        if (optionalCurrentCompany.isPresent()) {
            Role currentProduct = optionalCurrentCompany.get();

            if (element.getName() != null) {
                currentProduct.setName(element.getName());
            }


            return Optional.of((Role) this.repository.save(currentProduct));
        }
        return Optional.empty();

    }

    @Override
    public Page<Role> findAll(Pageable page, Role filter) {

        return this.roleRepository.findAll(page);
    }


}
