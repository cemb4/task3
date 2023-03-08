package jm.task.core.jdbc.util;



import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.SQLException;








public class Util {
    public static Connection getConnection(){
        try{
          Class.forName("org.postgresql.Driver");
         return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "8888");
        }catch (SQLException | ClassNotFoundException e){
         throw new RuntimeException(e);
        }
    }

    private static SessionFactory sessionFactory;
    public static SessionFactory getHybConnection() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "org.postgresql.Driver");
                properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
                properties.put(Environment.USER, "postgres");
                properties.put(Environment.PASS, "8888");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");


                configuration.addProperties(properties);
                configuration.addAnnotatedClass(User.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}



