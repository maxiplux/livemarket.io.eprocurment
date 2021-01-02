package io.eprocurment.b2b2021.services;


import io.eprocurment.b2b2021.models.products.ProductSku;
import io.eprocurment.b2b2021.services.impl.generics.CrudServices;

import java.util.Optional;

public interface ProductSkuService<T> extends CrudServices<T> {

    public Optional<ProductSku> UpdateById(String id, ProductSku source);

}


