package com.ltp.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "managers")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "role")
    private String role;

    @JsonIgnore
    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private Set<Department> departments = new HashSet<>();

    public Manager(Long id, String address, String city, User user) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.user = user;
    }

    public Manager(String address, String city, String role, User user) {
        this.address = address;
        this.city = city;
        this.role = role;
        this.user = user;
    }

    public Manager(String role, User user) {
        this.role = role;
        this.user = user;
    }

    public Manager() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Department> getDepartments() {
        return this.departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

}
