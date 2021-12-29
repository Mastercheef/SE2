package org.hbrs.se2.project.coll.entities;


import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "type")
@Table( name ="col_tab_user" , schema = "collhbrs" )
public class User {

    private int id;
    private String salutation;
    private String title;
    private String firstName;
    private String lastName;
    private Address address;
    private String phone;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private String type;
    private Settings settings;

    @Basic
    @Column(name = "salutation")
    public String getSalutation() {
        return salutation;
    }
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToOne
    @JoinColumn(name = "address_id")
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    @Basic
    @Column(name = "phone_number")
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "date_of_birth")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "mail_address")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }
    private String hashPassword(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    @Basic
    @Column(name = "type")
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Id
    @GeneratedValue(
            generator = "user_id"
    )
    @SequenceGenerator(
            name = "user_id",
            sequenceName = "collhbrs.col_seq_user_id"
    )
    @Column(name = "user_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "user", cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn
    public Settings getSettings() {
        return settings;
    }
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(address, user.address) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(dateOfBirth, user.dateOfBirth) &&
                Objects.equals(email, user.email) &&
                Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash( firstName, lastName, address, phone, dateOfBirth, email, password, id);
    }

    @Override
    public String toString() {
        return this.getId()  + ", " +
                this.getSalutation() + ", " +
                this.getTitle() + ", " +
                this.getFirstName() + ", " +
                this.getLastName() + ", " +
                this.getDateOfBirth()  + ", " +
                this.getPhone() + ", " +
                this.getAddress() + ", " +
                this.getEmail() + ", " +
                this.getType();
    }
}
