package io.eprocurment.b2b2021.services.impl;


import io.eprocurment.b2b2021.exceptions.IlegalParameterException;
import io.eprocurment.b2b2021.exceptions.ResourceNotFoundException;
import io.eprocurment.b2b2021.models.products.ProductSku;
import io.eprocurment.b2b2021.repository.ProductSkuRepository;
import io.eprocurment.b2b2021.services.ProductService;
import io.eprocurment.b2b2021.services.ProductSkuService;
import io.eprocurment.b2b2021.services.impl.generics.CrudServicesImpl;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service("ProductSku")
public class ProductSkuServiceImpl extends CrudServicesImpl<ProductSku> implements ProductSkuService<ProductSku> {


    @Autowired
    @Qualifier(value = "dozer")
    private Mapper mapperDozer;

    @Autowired
    private ProductSkuRepository productRepository;


    @PostConstruct
    public void posContructor() {
        this.setRepository(productRepository);
    }


    public Optional<ProductSku> UpdateById(String id, ProductSku source) {
        ProductSku currentUser = this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        mapperDozer.map(source, currentUser);
        this.repository.save(currentUser);
        return Optional.of(currentUser);
    }

    @Override
    public Optional<ProductSku> UpdateById(long id, ProductSku element) {
        throw  new IlegalParameterException("Method does not support ");

    }

    @Override
    public Page<ProductSku> findAll(Pageable page, ProductSku filter) {
        return null;
    }
}
