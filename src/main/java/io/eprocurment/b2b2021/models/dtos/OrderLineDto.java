package io.eprocurment.b2b2021.models.dtos;

import io.eprocurment.b2b2021.models.products.ProductSku;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDto {


    private String sku;
    private Double quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLineDto)) return false;
        OrderLineDto that = (OrderLineDto) o;
        return getSku().equals(that.getSku());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSku());
    }
}
