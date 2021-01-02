package io.eprocurment.b2b2021.services.impl;


import io.eprocurment.b2b2021.models.orders.Order;
import io.eprocurment.b2b2021.repository.OrderRepository;
import io.eprocurment.b2b2021.services.InvoiceServices;
import io.eprocurment.b2b2021.services.impl.generics.CrudServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class InvoiceServicesImpl extends CrudServicesImpl<Order> implements InvoiceServices<Order> {

    @Autowired
    private OrderRepository invoiceRepository;

    @PostConstruct
    public void posContructor() {
        this.setRepository(invoiceRepository);
    }


    public Optional<Order> UpdateById(long id, Order element) {
        Optional<Order> optionalCurrentCompany = this.repository.findById(id);
        if (optionalCurrentCompany.isPresent()) {
            Order currentProduct = optionalCurrentCompany.get();

            if (element.getCompany() != null) {
                currentProduct.setCompany(element.getCompany());
            }

            if (element.getComments() != null) {
                currentProduct.setComments(element.getComments());
            }

            if (element.getDescription() != null) {
                currentProduct.setDescription(element.getDescription());
            }

            if (element.getItems() != null) {
                currentProduct.setItems(element.getItems());
            }

            return Optional.of((Order) this.repository.save(currentProduct));
        }
        return Optional.empty();

    }

    @Override
    public Page<Order> findAll(Pageable page, Order filter) {
        return null;
    }


}
