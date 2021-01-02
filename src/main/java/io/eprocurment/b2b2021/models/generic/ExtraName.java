package io.eprocurment.b2b2021.models.generic;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ExtraName {

    private String name;
    private String description;
    private String value;
}
