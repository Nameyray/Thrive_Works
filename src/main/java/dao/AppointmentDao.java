package dao;

import interfaces.ThriveDatabaseDao;
import models.Appointment;
import models.Therapist;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentDao implements ThriveDatabaseDao<Appointment> {
    private final Sql2o sql2o;

    public AppointmentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Appointment data) {
        String query = "INSERT INTO appointments(starttime, endtime, createdat, patient, therapist, link ) values(:starttime, :endtime, now(), :patient, :therapist, :link)";
        try(Connection connection = sql2o.open()){
            int id =(int) connection.createQuery(query, true)
                    .bind(data)
                    .executeUpdate()
                    .getKey();
            data.setId(id);
        }catch(Sql2oException ex){
            ex.printStackTrace();
        }


    }

    @Override
    public void update(Appointment data) {
        String query = "UPDATE appointments SET (starttime, endtime, patient, therapist, link) = (:starttime, :endtime, :patient, :therapist, :link) WHERE id = :id";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .bind(data)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }

    }

    @Override
    public List<Appointment> findAll() {
        String query = "SELECT * FROM appointments";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .executeAndFetch(Appointment.class);
        } catch (Sql2oException ex){
            return new ArrayList<>();
        }
    }


    @Override
    public Appointment findById(int id) {
        String query = "SELECT * FROM appointments WHERE id = :id ";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Appointment.class);
        } catch (Sql2oException ex){
            ex.printStackTrace();
            return new Appointment(new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()),0, 0,"");
        }

    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM appointments WHERE id = :id ";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void delete() {
        String query = "DELETE FROM appointments";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }

    }
}
