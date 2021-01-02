package io.eprocurment.b2b2021.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    String username;
    String password;
    String grant_type;

    public UserDTO(String username, String password, String grant_type) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
    }
}
