package dao;

import interfaces.ThriveDatabaseDao;
import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Method to login
    public Map<String, Object> login(String email, String password){
        String selectQuery = "SELECT * FROM users WHERE email = :email AND password = :password";
        Map<String, Object> result = new HashMap<>();
        try(Connection connection = sql2o.open()){
            Patient patient = connection.createQuery(selectQuery)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Patient.class);
            if(patient != null){
                result.put("login", true);
                result.put("current", patient);
            } else {
                result.put("login", false);
            }
        } catch (Sql2oException exception){
            exception.printStackTrace();
        }
        return result;
    }

    // Method to signup
    public void signup(Patient patient){
        add(patient);
    }

    // Method to capture survey questions
    public Map<String, Object> captureSurveyResponses(String gender, String age, String relationship, String seenTherapist, String physicalHealth, String eatingHabits, String financialStatus, String language, List<String> preferred){
        Map<String, Object> surveyResponses = new HashMap<>();
        surveyResponses.put("gender", gender);
        surveyResponses.put("age", age);
        surveyResponses.put("relationship", relationship);
        surveyResponses.put("seen_therapist", seenTherapist);
        surveyResponses.put("physical_health", physicalHealth);
        surveyResponses.put("eating_habits", eatingHabits);
        surveyResponses.put("financial_status", financialStatus);
        surveyResponses.put("language", language);
        surveyResponses.put("preferred", preferred);
        return surveyResponses;
    }
}
