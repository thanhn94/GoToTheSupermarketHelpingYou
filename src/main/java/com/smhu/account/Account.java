package com.smhu.account;

import java.sql.Date;

public class Account {

    private String id;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private Date dob;
    private String role;
    private int numSuccess;
    private int numCancel;
    private double wallet;

    public Account() {
    }

    public Account(String id, String username, String firstName, String middleName, String lastName,
            String email, String phone, Date dob, String role, int numSuccess, int numCancel,
            double wallet) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.role = role;
        this.numSuccess = numSuccess;
        this.numCancel = numCancel;
        this.wallet = wallet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getNumSuccess() {
        return numSuccess;
    }

    public void setNumSuccess(int numSuccess) {
        this.numSuccess = numSuccess;
    }

    public int getNumCancel() {
        return numCancel;
    }

    public void setNumCancel(int numCancel) {
        this.numCancel = numCancel;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }
}
