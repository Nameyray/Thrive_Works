package dao;

import interfaces.ThriveDatabaseDao;
import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class PatientDao implements ThriveDatabaseDao<Patient> {
    private final Sql2o sql2o;

    public PatientDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
//Method to add a new patient
    @Override
    public void add(Patient data) {
        String query = "INSERT INTO users(name, phone, address, email, role, password) values(:name, :phone, :address, :email, :role, :password)";
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

    //method to update the patient
    @Override
    public void update(Patient data) {
        String query = "UPDATE users SET (name, phone, address) = (:name, :phone, :address) WHERE id = :id";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .bind(data)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }

    }

    //method to list patients
    @Override
    public List<Patient> findAll() {
        String query = "SELECT * FROM users WHERE role = 'Patient'";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Patient.class);
        } catch (Sql2oException ex){
            return new ArrayList<>();
        }
    }

    @Override
    public Patient findById(int id) {
        String query = "SELECT * FROM users WHERE id = :id AND role = 'Patient'";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Patient.class);
        } catch (Sql2oException ex){
            ex.printStackTrace();
            return new Patient("",0, "", "", "");
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM users WHERE id = :id AND role='Patient'";
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
        String query = "DELETE FROM users WHERE role='Patient'";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }
    }
}
