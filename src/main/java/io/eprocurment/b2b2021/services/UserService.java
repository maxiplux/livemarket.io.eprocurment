package io.eprocurment.b2b2021.services;


import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.models.users.User;
import io.eprocurment.b2b2021.services.impl.generics.CrudServices;

import java.util.Optional;

public interface UserService extends CrudServices<User> {

    User findByUsername(String username);

    User save(User user);

    Boolean existsByUsername(String username);

    boolean existsByEmail(String username);

    Optional<Company> findCompanyByUserId(Long username);

    void areOkayUsernameOrEmail(User user);
}
