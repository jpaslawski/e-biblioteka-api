package com.jpas.ebiblioteka.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "user_contact")
public class UserContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Integer id;

    @Column(name = "contact_address")
    private String address;

    @Column(name = "contact_city")
    private String city;

    @Column(name = "contact_zipCode")
    private String zipCode;

    @Column(name = "contact_phoneNumber")
    private Long phoneNumber;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "contact_owner")
    private User contactOwner;

    public UserContact() {

    }

    public UserContact(String address, String city, String zipCode, Long phoneNumber) {
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getContactOwner() {
        return contactOwner;
    }

    public void setContactOwner(User contactOwner) {
        this.contactOwner = contactOwner;
    }

    @Override
    public String toString() {
        return "UserContact{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", contactOwner=" + contactOwner +
                '}';
    }
}
