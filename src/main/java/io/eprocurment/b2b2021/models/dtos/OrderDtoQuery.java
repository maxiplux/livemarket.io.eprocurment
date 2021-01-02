package io.eprocurment.b2b2021.models.dtos;

import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.models.rules.State;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDtoQuery {
    private Date createdAt;

    private Date createdAtStart;
    private Date createdAtEnd;

    private Long OrderId;
    private Date updatedAt;
    private Company company;
    private State orderStatus;

    public Boolean isByRange() {
        return this.createdAtStart != null && this.createdAtEnd != null;
    }

}
