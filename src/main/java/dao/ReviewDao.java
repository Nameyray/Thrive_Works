package dao;

import interfaces.ThriveDatabaseDao;
import models.Appointment;
import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewDao implements ThriveDatabaseDao<Review> {
    private final Sql2o sql2o;

    public ReviewDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Review data) {
        String query = "INSERT INTO reviews(appointmentid, feedback) values(:appointmentid, :feedback)";
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
    public void update(Review data) {
        String query = "UPDATE reviews SET (appointmentid, feedback) = (:appointmentid, :feedback) WHERE id = :id";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .bind(data)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }

    }

    @Override
    public List<Review> findAll() {
        String query = "SELECT * FROM reviews";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .executeAndFetch(Review.class);
        } catch (Sql2oException ex){
            return new ArrayList<>();
        }

    }

    @Override
    public Review findById(int id) {
        String query = "SELECT * FROM reviews WHERE id = :id ";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Review.class);
        } catch (Sql2oException ex){
            ex.printStackTrace();
            return new Review(0,"");
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM reviews WHERE id = :id ";
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
        String query = "DELETE FROM reviews";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }


    }
}
