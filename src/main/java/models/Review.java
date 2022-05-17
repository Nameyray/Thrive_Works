package models;

public class Review {
    private int id;
    private int appointmentid;
    private String feedback;

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
