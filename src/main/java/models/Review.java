package models;

import java.util.Objects;

public class Review {
    private int id;
    private int appointmentid;
    private String feedback;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return getAppointmentid() == review.getAppointmentid() && Objects.equals(getFeedback(), review.getFeedback());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppointmentid(), getFeedback());
    }

    public Review(int appointmentid, String feedback) {
        this.appointmentid = appointmentid;
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(int appointmentid) {
        this.appointmentid = appointmentid;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
