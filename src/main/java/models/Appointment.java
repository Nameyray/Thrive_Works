package models;

import org.joda.time.LocalDateTime;

import java.sql.Timestamp;
import java.util.Objects;

public class Appointment {
    private int id;
    private Timestamp starttime;
    private Timestamp endtime;
    private Timestamp createdat;
    private int patient;
    private int therapist;
    private String link;
    private LocalDateTime formattedStartTime;
    private LocalDateTime formattedEndTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return getPatient() == that.getPatient() && getTherapist() == that.getTherapist() && Objects.equals(getStarttime(), that.getStarttime()) && Objects.equals(getEndtime(), that.getEndtime()) && Objects.equals(getLink(), that.getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStarttime(), getEndtime(), getPatient(), getTherapist(), getLink());
    }

    public Appointment(Timestamp starttime, Timestamp endtime, int patient, int therapist, String link) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.patient = patient;
        this.therapist = therapist;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public int getTherapist() {
        return therapist;
    }

    public void setTherapist(int therapist) {
        this.therapist = therapist;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getFormattedStartTime() {
        return formattedStartTime;
    }

    public void setFormattedStartTime(LocalDateTime formattedStartTime) {
        this.formattedStartTime = formattedStartTime;
    }

    public LocalDateTime getFormattedEndTime() {
        return formattedEndTime;
    }

    public void setFormattedEndTime(LocalDateTime formattedEndTime) {
        this.formattedEndTime = formattedEndTime;
    }
}
