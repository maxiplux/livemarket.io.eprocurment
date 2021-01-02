package io.eprocurment.b2b2021.models.generic;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor

//public class Contact    {
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    long serialversionUID = 79234897L;

    //@JsonFormat(shape = JsonFormat.Shape.STRING)

    private String fullName;


    private String email;


    //@JsonFormat(shape = JsonFormat.Shape.STRING)

    private String phone;
}
