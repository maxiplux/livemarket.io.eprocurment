package io.eprocurment.b2b2021.models.corporate;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import io.eprocurment.b2b2021.models.AuditModel;
import io.eprocurment.b2b2021.models.users.User;
import io.eprocurment.b2b2021.models.generic.Address;
import io.eprocurment.b2b2021.models.generic.Contact;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity

@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)


public class Company extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    private String name;

    @Email
    private String email;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Address primaryAddress;

    @NotBlank
    private String primaryPhone;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private User manager;

    @ManyToOne
    private Company mainOffice;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Contact> contacts = new ArrayList<Contact>();

    public void clearContacts() {

        this.contacts = new ArrayList<Contact>();
    }


}
