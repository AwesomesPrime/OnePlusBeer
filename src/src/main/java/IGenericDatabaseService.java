import java.util.List;

/**
 * Created by Stefan on 25.06.2018.
 */
public interface IGenericDatabaseService<T> {
    <T> T get(Class<T> type,int id);

    <T> List<T> getAll(Class<T> type);

    <T> void save (T entity);

    <T> void delete (T entity);
}
