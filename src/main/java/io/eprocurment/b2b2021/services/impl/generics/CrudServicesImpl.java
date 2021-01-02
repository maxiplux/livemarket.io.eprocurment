package io.eprocurment.b2b2021.services.impl.generics;

import io.eprocurment.b2b2021.exceptions.ResourceNotFoundException;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Data
@Log4j2
@Transactional(rollbackFor = Exception.class)
public abstract class CrudServicesImpl<T> implements CrudServices<T> {


    protected EntityManager entityManagerBase;

    protected PagingAndSortingRepository repository;


    public CrudServicesImpl(PagingAndSortingRepository repository) {
        this.repository = repository;
    }

    public CrudServicesImpl() {

    }


    @Override
    //@Cacheable(value = "data", key = "#pageable")
    public Page<T> findAll(Pageable pageable) {

        return this.repository.findAll(pageable);
    }

    @Override
    public T create(T elememnt) {
        return (T) this.repository.save(elememnt);
    }


    @Override
    public abstract Optional<T> UpdateById(long id, T element);


    @Override
    public Boolean deleteById(long id) {
        this.repository.deleteById(id);
        return this.repository.findById(id).isPresent();
    }


    @SneakyThrows
    @Override
    //@
    // (value = "data_show", key = "#id") todo:fix caches
    public T show(long id) {
        return (T) this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @Override

    public Optional<T> findById(long id) {
        return this.repository.findById(id);
    }

    @Override
    public abstract Page<T> findAll(Pageable page, T filter);

}
