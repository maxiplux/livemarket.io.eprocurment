package io.eprocurment.b2b2021.models.orders;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.eprocurment.b2b2021.models.AuditModel;
import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.models.rules.State;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderEntity")
public class Order extends AuditModel {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    // Mapping throght Id company, because Json Serialize can not do that
    private Map<Long, List<String>> comments = new HashMap<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;




    @ManyToOne(fetch = FetchType.EAGER)
    private State orderStatus;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Set<OrderLine> items = new HashSet<>();

    public void add(List<OrderLine> items) {
        this.items.addAll(items);
    }

    public void add(OrderLine item) {
        this.items.add(item);
    }


    public void clearOrderLines() {
        items = new HashSet<>();
    }

    public void clearComments() {
        comments = new HashMap<>();
    }


    public void addComment(Company company, String comment) {

        List<String> comments = new ArrayList<String>();
        if (!this.comments.containsKey(company.getId())) {

            this.comments.put(company.getId(), comments);

        }
        comments = this.comments.get(company.getId());
        comments.add(comment);
        this.comments.put(company.getId(), comments);

    }
}
