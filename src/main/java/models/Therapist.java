package models;

import base.User;

public class Therapist extends User {
    private String specialization;
    private int ratings;
    private int is_vetted;

    private static final String USER_ROLE = "Therapist";
    public Therapist(String specialization, String name, int phone, String address, String email, String password) {
        this.specialization = specialization;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.ratings = 0;
        this.is_vetted = 0;
        this.role = USER_ROLE;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public int getIs_vetted() {
        return is_vetted;
    }

    public void setIs_vetted(int is_vetted) {
        this.is_vetted = is_vetted;
    }
}
