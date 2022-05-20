package dao;

import interfaces.ThriveDatabaseDao;
import models.Therapist;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TherapistDao implements ThriveDatabaseDao<Therapist> {
    private final Sql2o sql2o;

    public TherapistDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Therapist data) {
        String query = "INSERT INTO users(name, phone, address, email, role, password, specialization, description, rate, ratings, is_vetted ) values(:name, :phone, :address, :email, :role, :password, :specialization, :description, :rate, :ratings, :is_vetted)";
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
    public void update(Therapist data) {
        String query = "UPDATE users SET (name, phone, address, specialization, description, ratings, is_vetted) = (:name, :phone, :address, :specialization, :description, :ratings, :is_vetted) WHERE id = :id";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .bind(data)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }


    }

    @Override
    public List<Therapist> findAll() {
        String query = "SELECT * FROM users WHERE role = 'Therapist'";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Therapist.class);
        } catch (Sql2oException ex){
            return new ArrayList<>();
        }
    }

    @Override
    public Therapist findById(int id) {
        String query = "SELECT * FROM users WHERE id = :id AND role = 'Therapist'";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Therapist.class);
        } catch (Sql2oException ex){
            ex.printStackTrace();
            return new Therapist("","",0,"","","", 0, "");
        }

    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM users WHERE id = :id AND role='Therapist'";
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
        String query = "DELETE FROM users WHERE role='Therapist'";
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
            Therapist therapist = connection.createQuery(selectQuery)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Therapist.class);
            if(therapist != null){
                result.put("login", true);
                result.put("current", therapist);
            } else {
                result.put("login", false);
            }
        } catch (Sql2oException exception){
            exception.printStackTrace();
        }
        return result;
    }

    // Method to signup
    public void signup(Therapist therapist){
        add(therapist);
    }

    // Method to select therapist
    public List<Therapist> selectTherapists(Map<String, Object> surveyResponses){
        List<Therapist> therapists;
        if(surveyResponses.get("financial_status").equals("Just there")){
            if (surveyResponses.get("physical_health").equals("Just there") || surveyResponses.get("eating_habits").equals("Just there")){
                String selectQuery = "SELECT * FROM users WHERE rate <= 2000 AND (specialization = 'Depression' OR specialization = 'Anxiety' OR specialization = 'Stress') AND is_vetted = 1 ORDER BY ratings DESC LIMIT 3";
                try(Connection connection = sql2o.open()){
                    therapists = connection.createQuery(selectQuery)
                            .throwOnMappingFailure(false)
                            .executeAndFetch(Therapist.class);
                } catch (Sql2oException exception){
                    exception.printStackTrace();
                    therapists = new ArrayList<>();
                }
            } else {
                String selectQuery = "SELECT * FROM users WHERE rate <= 2000 AND is_vetted = 1 ORDER BY ratings LIMIT 3";
                try(Connection connection = sql2o.open()){
                    therapists = connection.createQuery(selectQuery)
                            .throwOnMappingFailure(false)
                            .executeAndFetch(Therapist.class);
                } catch (Sql2oException exception){
                    exception.printStackTrace();
                    therapists = new ArrayList<>();
                }
            }
        } else if (surveyResponses.get("financial_status").equals("In between")){
            if (surveyResponses.get("physical_health").equals("Just there") || surveyResponses.get("eating_habits").equals("Just there")){
                String selectQuery = "SELECT * FROM users WHERE rate > 2000 AND rate <= 5000 AND (specialization = 'Depression' OR specialization = 'Anxiety' OR specialization = 'Stress') AND is_vetted = 1 ORDER BY ratings DESC LIMIT 3";
                try(Connection connection = sql2o.open()){
                    therapists = connection.createQuery(selectQuery)
                            .throwOnMappingFailure(false)
                            .executeAndFetch(Therapist.class);
                } catch (Sql2oException exception){
                    exception.printStackTrace();
                    therapists = new ArrayList<>();
                }
            } else {
                String selectQuery = "SELECT * FROM users WHERE rate > 2000 AND rate <= 10000 AND is_vetted = 1 ORDER BY ratings LIMIT 3";
                try(Connection connection = sql2o.open()){
                    therapists = connection.createQuery(selectQuery)
                            .throwOnMappingFailure(false)
                            .executeAndFetch(Therapist.class);
                } catch (Sql2oException exception){
                    exception.printStackTrace();
                    therapists = new ArrayList<>();
                }
            }
        } else {
            if (surveyResponses.get("physical_health").equals("Just there") || surveyResponses.get("eating_habits").equals("Just there")){
                String selectQuery = "SELECT * FROM users WHERE rate > 10000 AND (specialization = 'Depression' OR specialization = 'Anxiety' OR specialization = 'Stress') AND is_vetted = 1 ORDER BY ratings DESC LIMIT 3";
                try(Connection connection = sql2o.open()){
                    therapists = connection.createQuery(selectQuery)
                            .throwOnMappingFailure(false)
                            .executeAndFetch(Therapist.class);
                } catch (Sql2oException exception){
                    exception.printStackTrace();
                    therapists = new ArrayList<>();
                }
            } else {
                String selectQuery = "SELECT * FROM users WHERE rate > 10000 AND is_vetted = 1 ORDER BY ratings LIMIT 3";
                try(Connection connection = sql2o.open()){
                    therapists = connection.createQuery(selectQuery)
                            .throwOnMappingFailure(false)
                            .executeAndFetch(Therapist.class);
                } catch (Sql2oException exception){
                    exception.printStackTrace();
                    therapists = new ArrayList<>();
                }
            }
        }
        return therapists;
    }

    // Method to calculate rating
    public void computeRating(int therapistId, int rating){
        Therapist therapist = findById(therapistId);
        int currentRating = therapist.getRatings();
        int newRating = currentRating + rating;
        therapist.setRatings(newRating);
        update(therapist);
    }
}
