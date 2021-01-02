package io.eprocurment.b2b2021.repository;


import io.eprocurment.b2b2021.models.users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAll();

    @Query("select u from User u where u.username=?1")
    User findByUsername2(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByUsernameOrEmail(String username, String email);
}
