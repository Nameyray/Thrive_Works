package dao;

import interfaces.ThriveDatabaseDao;
import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class PatientDao implements ThriveDatabaseDao<Patient> {
    private final Sql2o sql2o;

    public PatientDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
//Method to add a new patient
    @Override
    public void add(Patient data) {
        String query = "INSERT INTO users(name, phone, address,email, password) values(:name, :phone, :address, :email, :password)";
        try(Connection connection = sql2o.open()){
            int id =(int) connection.createQuery(query, true)
                    .bind(data)
                    .executeUpdate()
                    .getKey();
            data.setId(id);
        }catch(Sql2oException ex ){
            ex.printStackTrace();
        }

    }

    //method to update the patient
    @Override
    public void update(Patient data) {
        String query = "UPDATE users SET (name, phone, address) = (:name, :phone, :address)";


    }

    @Override
    public List<Patient> findAll() {
        return null;
    }

    @Override
    public Patient findById(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete() {

    }
}
