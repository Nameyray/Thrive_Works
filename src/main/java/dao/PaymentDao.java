package dao;

import interfaces.ThriveDatabaseDao;
import models.Appointment;
import models.Payment;
import models.Therapist;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentDao implements ThriveDatabaseDao<Payment> {
    private final Sql2o sql2o;

    public PaymentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Payment data) {
        String query = "INSERT INTO payments(appointmentid, amount ) values(:appointmentid, :amount)";
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
    public void update(Payment data) {
        String query = "UPDATE payments SET (appointmentid, amount) = (:appointmentid, :amount) WHERE id = :id";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .bind(data)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }

    }

    @Override
    public List<Payment> findAll() {
        String query = "SELECT * FROM payments";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .executeAndFetch(Payment.class);
        } catch (Sql2oException ex){
            return new ArrayList<>();
        }
    }

    @Override
    public Payment findById(int id) {
        String query = "SELECT * FROM payments WHERE id = :id ";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Payment.class);
        } catch (Sql2oException ex){
            ex.printStackTrace();
            return new Payment(0,0.00);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM payments WHERE id = :id ";
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
        String query = "DELETE FROM payments ";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .executeUpdate();
        } catch (Sql2oException ex){
            ex.printStackTrace();
        }


    }
}
