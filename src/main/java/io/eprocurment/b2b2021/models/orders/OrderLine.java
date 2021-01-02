package io.eprocurment.b2b2021.models.orders;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.eprocurment.b2b2021.models.AuditModel;
import io.eprocurment.b2b2021.models.products.ProductSku;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
public class OrderLine extends AuditModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Order orderHeader;


    @ManyToOne(fetch = FetchType.LAZY)
    private ProductSku product;

    private Double price;
    private Double quantity;
    private Double tax;




}
