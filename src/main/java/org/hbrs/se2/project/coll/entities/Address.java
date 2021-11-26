package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;

@Entity
@Table( name ="col_tab_address" , schema = "collhbrs" )
public class Address {
    private int id;
    private String postalCode;
    private String city;
    private String country;
    private String street;
    private String houseNumber;

    @Id
    @GeneratedValue(
            generator = "address_id"
    )
    @SequenceGenerator(
            name = "address_id",
            sequenceName = "collhbrs.col_seq_address_id"
    )
    @Column(name = "address_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "postal_code")
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "house_number")
    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return street + " "  + houseNumber + "\n"
                + postalCode + " " + city + "\n"
                + country;
    }

}
