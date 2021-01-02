package io.eprocurment.b2b2021.models.users;

import io.eprocurment.b2b2021.models.AuditModel;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Table(name = "roles")
public class Role extends AuditModel {


    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50)
    private String name;


}
