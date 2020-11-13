package com.recorda.admin.users.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This is a User for Recardo e-commerce platform
 */
@Document(collection = "#{@environment.getProperty('mongodb.users.collection')}")
public class User {

    @Id
    private String id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String address;

    @Override
    public String toString() {
        return "User [ id=" + id
                + ", firstname= " + firstname
                + ", email=" + email
                + ", address=" + address
                + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
