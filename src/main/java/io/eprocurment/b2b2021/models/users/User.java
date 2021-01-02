package io.eprocurment.b2b2021.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.eprocurment.b2b2021.models.AuditModel;
import io.eprocurment.b2b2021.models.enums.RoleName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Entity
@Table(name = "appuser")
@Data
@EqualsAndHashCode(callSuper = false)

@NoArgsConstructor
public class User extends AuditModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 20)
    private String username;

    @Column(length = 60)
    @NotBlank(message = "Password is mandatory!")
    @JsonIgnore
    private String password;

    private Boolean enabled;
    @NotBlank(message = "Firts Name is mandatory!")
    private String firtsName;
    @NotBlank(message = "Last Name is mandatory!")
    private String lastName;
    @Column(unique = false)
    @Email(message = "Check The format for this email")
    private String email;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_rols", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
    private List<Role> roles;

    public Collection<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> collect) {
        Optional.ofNullable(collect).ifPresent(currentRoles -> {

            currentRoles.forEach(role -> {
                this.roles.add(role);
            });
        });


    }

    public void clearRoles() {
        this.roles = new ArrayList<>();
    }

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(role);
    }

    public Boolean isCompanyManager() {
        if (this.roles == null) {
            return false;
        }
        return this.roles.stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_COMPANY_MANAGER));
    }
}
