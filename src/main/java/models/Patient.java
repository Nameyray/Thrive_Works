package models;

import base.User;

public class Patient extends User {
    public Patient(String name, int phone, String address, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
    }
}
