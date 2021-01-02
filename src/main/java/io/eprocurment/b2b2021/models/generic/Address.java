package io.eprocurment.b2b2021.models.generic;

import lombok.Data;


@Data
public class Address {
    private static final long serialversionUID = 79234897L;
    private String name;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
}
