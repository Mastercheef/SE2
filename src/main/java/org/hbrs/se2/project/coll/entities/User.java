package org.hbrs.se2.project.coll.entities;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Table( name ="col_tab_user" , schema = "collhbrs" )
public class User {
    private String firstName;
    private String lastName;
    private Address address;
    private String phone;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private int userid;

    //private List<Role> roles;


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
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

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
        this.password = password;
    }

    @Id
    @GeneratedValue(
            generator = "user_id"
    )
    @SequenceGenerator(
            name = "user_id",
            sequenceName = "collhbrs.col_seq_user_id"
    )
    @Column(name = "user_id")
    public int getUserId() {
        return userid;
    }
    public void setUserId(int userid) {
        this.userid = userid;
    }

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_to_rolle", catalog = "demouser",
            schema = "collhbrs",
            joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "bezeichnung", referencedColumnName = "bezeichhnung", nullable = false))
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }*/

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
                Objects.equals(password, user.password) &&
                Objects.equals(userid, user.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash( firstName, lastName, address, phone, dateOfBirth, email, password, userid);
    }
}
