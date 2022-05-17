package models;

import java.util.Objects;

public class Payment {
    private int id;
    private int appointmentid;
    private double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return getAppointmentid() == payment.getAppointmentid() && Double.compare(payment.getAmount(), getAmount()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppointmentid(), getAmount());
    }

    public Payment(int appointmentid, double amount) {
        this.appointmentid = appointmentid;
        this.amount = amount;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
