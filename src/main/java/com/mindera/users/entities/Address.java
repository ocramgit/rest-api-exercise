package com.mindera.users.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Address implements Serializable {

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private Integer number;

    public Address(String country, String city, String street, Integer number) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public Address() {
    }

    public Integer getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }
}