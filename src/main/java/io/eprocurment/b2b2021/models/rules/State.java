package io.eprocurment.b2b2021.models.rules;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.eprocurment.b2b2021.models.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
public class State extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;


    private Integer sequence;


    @OneToMany
    private Set<State> nextStates = new HashSet<State>();

    @JsonIgnore
    private Boolean sendEmilToAdmin;
    @JsonIgnore
    private Boolean sendEmilToClient;

    @JsonIgnore
    private Boolean sendEmilToClientUser;
    @JsonIgnore
    private Boolean sendEmilToStaff;

    public State() {

    }


    public void addState(State state) {
        if (this.nextStates == null) {
            this.nextStates = new HashSet<State>();
        }
        this.nextStates.add(state);
    }


}

