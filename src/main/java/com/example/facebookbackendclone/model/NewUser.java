package com.example.facebookbackendclone.model;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;


public class NewUser {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String dob;
    private String country;
    private String gender;
    private String password;
    private Set<String> role;

    public NewUser() {

    }

    public NewUser(int id, String firstName, String lastName, String email, String dob, String country, String gender, String password, Set<String> role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.country = country;
        this.gender = gender;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
