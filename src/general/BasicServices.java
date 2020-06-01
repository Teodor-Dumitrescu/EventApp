package general;
import java.util.List;

public interface BasicServices<T> {

     List<T> getAll();
     default T get(int index, int type){
          System.out.println("ERROR [Default call in BasicServices interface function]");
          return null;
     };
     default T get(int index){
          System.out.println("ERROR [Default call in BasicServices interface function]");
          return null;
     };
     void add(T entity);
     void update(T entity);
     void remove(T entity);
     void show();
}
