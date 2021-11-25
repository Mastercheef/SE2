package org.hbrs.se2.project.coll.entities;

import javax.persistence.*;

@Entity
@Table( name ="col_tab_address" , schema = "collhbrs" )
public class Address {
    private int id;
    private String postalcode;
    private String city;
    private String country;
    private String street;
    private String housenumber;

    @Id
    @GeneratedValue(
            generator = "address_id"
    )
    @SequenceGenerator(
            name = "address_id",
            sequenceName = "collhbrs.col_seq_address_id"
    )
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "postal_code")
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
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
    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

}
