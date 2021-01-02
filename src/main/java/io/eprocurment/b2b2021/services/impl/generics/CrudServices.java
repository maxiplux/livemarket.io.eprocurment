package io.eprocurment.b2b2021.services.impl.generics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CrudServices<T> {

    Page<T> findAll(Pageable pageable, T filter);

    Page<T> findAll(Pageable pageable);

    T create(T elememnt);


    Optional<T> UpdateById(long id, T element);

    Boolean deleteById(long id);

    Optional<T> findById(long id);

    T show(long id);
}
