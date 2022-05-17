package interfaces;

import java.util.List;

public interface ThriveDatabaseDao<T>{
    //CRUD
    void add(T data);
    void update(T data);
    List<T> findAll();
    T findById(int id);
    void delete(int id);
    void delete();

}

