package com.piotrnowicki.interceptors.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@NamedQuery(name = Customer.FIND_ALL, query = "SELECT c FROM Customer c")
public class Customer {

    public final static String FIND_ALL = "Customer.findAll";

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    // -------------------------------------------------------------------------------||
    // Constructors ------------------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    /**
     * For JPA purposes only.
     */
    Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // -------------------------------------------------------------------------------||
    // Getters and setters -----------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    public Long getId() {
        return id;
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

    // -------------------------------------------------------------------------------||
    // Contract ----------------------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);
        
        builder.append("id", id);
        builder.append("firstName", firstName);
        builder.append("lastName", lastName);
        
        return builder.toString();
    }

}
