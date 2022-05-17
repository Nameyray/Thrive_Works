package dao;

import interfaces.ThriveDatabaseDao;
import models.Patient;
import models.Therapist;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class TherapistDao implements ThriveDatabaseDao<Therapist> {
    private final Sql2o sql2o;

    public TherapistDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Therapist data) {
        String query = "INSERT INTO users(name, phone, address, email, role, password, specialization, ratings, is_vetted ) values(:name, :phone, :address, :email, :role, :password, :specialization, :ratings, :is_vetted)";
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
        String query = "UPDATE users SET (name, phone, address, specialization, ratings, is_vetted) = (:name, :phone, :address, :specialization, :ratings, :is_vetted) WHERE id = :id";
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
            return new Therapist("","",0,"","","");
        }

    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM users WHERE id = :id";
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
        String query = "DELETE FROM users";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }

    }
}
