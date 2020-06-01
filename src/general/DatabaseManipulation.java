package general;

import java.sql.ResultSet;
import java.util.List;

public interface DatabaseManipulation<T> {

   default List<T> getData(){
       System.out.println("ERROR [Default call in DatabaseManipulation interface function]");
       return null;
   };
   default List<T> getData(int type){
       System.out.println("ERROR [Default call in DatabaseManipulation interface function]");
       return null;
   };

   default T get(int index) {
       System.out.println("ERROR [Default call in DatabaseManipulation interface function]");
       return null;
   }
   default T get(int index, int type) {
       System.out.println("ERROR [Default call in DatabaseManipulation interface function]");
        return null;
   }

  T parseElement(ResultSet resultSet);
   void insert(T entity);
   void update(T entity);
   void delete(T entity);
}
