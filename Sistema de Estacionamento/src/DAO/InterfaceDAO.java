package DAO;
import java.util.List;

public interface InterfaceDAO<T> {

    boolean insert(T objetoModelo);

    boolean delete(int id);

    boolean update(T objetoModelo);

    List<T> list(int limit, int offset);

    T get(int id);
}
