package io.eprocurment.b2b2021.models.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.eprocurment.b2b2021.models.AuditModel;
import io.eprocurment.b2b2021.models.generic.ExtraName;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProductSku extends AuditModel {


    @Id
    private String sku;

    @NotBlank
    private String description;


    @JsonIgnoreProperties(value = {"categories", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Product product;


    @Min(value = 0)
    private Double price;

    private String picture;


    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<ExtraName> extras = new ArrayList<ExtraName>();


    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<String> tags = new ArrayList<String>();


}
