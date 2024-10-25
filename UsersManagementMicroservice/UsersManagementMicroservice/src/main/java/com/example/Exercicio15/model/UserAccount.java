package com.example.Exercicio15.model;

import com.example.Exercicio15.enumerator.UserRole;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.Date;
import java.util.List;

@Entity
@Audited
public class UserAccount extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(nullable = false, unique = true)
    private int phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private int nif;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotAudited
    @OneToMany(mappedBy = "userAccount", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Address> addresses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        if (addresses != null) {
            this.addresses = addresses;
            for (Address address : addresses) {
                address.setUserAccount(this);
            }
        }
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
        address.setUserAccount(null);
    }
}