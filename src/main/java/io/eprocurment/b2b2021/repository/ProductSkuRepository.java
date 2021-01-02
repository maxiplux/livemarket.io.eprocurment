package io.eprocurment.b2b2021.repository;


import io.eprocurment.b2b2021.models.products.ProductSku;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductSkuRepository extends PagingAndSortingRepository<ProductSku, String> {


}
