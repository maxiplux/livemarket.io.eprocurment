package io.eprocurment.b2b2021.services.impl;


import io.eprocurment.b2b2021.exceptions.ResourceNotFoundException;
import io.eprocurment.b2b2021.models.products.Product;
import io.eprocurment.b2b2021.repository.ProductRepository;
import io.eprocurment.b2b2021.services.ProductService;
import io.eprocurment.b2b2021.services.impl.generics.CrudServicesImpl;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service("ProductServices")
public class ProductServiceImpl extends CrudServicesImpl<Product> implements ProductService<Product> {


    @Autowired
    @Qualifier(value = "dozer")
    private Mapper mapperDozer;

    @Autowired
    private ProductRepository productRepository;


    @PostConstruct
    public void posContructor() {
        this.setRepository(productRepository);
    }

    @Override
    public Optional<Product> UpdateById(long id, Product source) {
        Product currentUser = this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        mapperDozer.map(source, currentUser);
        this.repository.save(currentUser);
        return Optional.of(currentUser);
    }

    @Override
    public Page<Product> findAll(Pageable page, Product filter) {
        return null;
    }
}
