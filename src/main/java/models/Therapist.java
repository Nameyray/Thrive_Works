package models;

import base.User;

public class Therapist extends User {
    private String specialization;

    private String description;
    private int ratings;
    private int is_vetted;
    private int rate;

    private static final String USER_ROLE = "Therapist";
    public Therapist(String specialization, String name, int phone, String address, String email, String password, int rate, String description) {
        this.specialization = specialization;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.ratings = 0;
        this.is_vetted = 0;
        this.rate = rate;
        this.description = description;
        this.role = USER_ROLE;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
