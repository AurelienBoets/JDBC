package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;
    private String lastName;
    private String firstName;
    private List<Account> accounts=new ArrayList<>();
    private String phone;

    public User() {

    }

    public User(long id, String lastName, String firstName, String phone) {
        this(lastName,firstName,phone);
        this.id = id;
    }

    public User( String lastName, String firstName,String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(long id) {
        this.id = id;
    }
}
