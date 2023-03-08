package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getHybConnection();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            String str = """
                    create table if not exists Users(
                    id serial primary key,
                    name varchar(100),
                    lastName varchar(100),
                    age smallint)
                    """;
            Query query = session.createNativeQuery(str);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            String str = "DROP TABLE IF EXISTS users";
            Query query = session.createNativeQuery(str);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.flush();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = null;
        try(Session session = sessionFactory.openSession()) {

            session.beginTransaction();
           usersList = session.createQuery("FROM User").getResultList();


            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String str = "TRUNCATE TABLE users";
            Query query = session.createNativeQuery(str);
            query.executeUpdate();

            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
