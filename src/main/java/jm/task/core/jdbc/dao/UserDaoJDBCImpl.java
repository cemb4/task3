package jm.task.core.jdbc.dao;

import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection conn = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
    try(Statement stat = conn.createStatement()) {
        String str = """
                create table if not exists Users(
                id serial primary key,
                name varchar(100),
                lastName varchar(100),
                age smallint)
                """;
        stat.executeUpdate(str);
    }catch (SQLException e){
        e.printStackTrace();
    }
    }

    public void dropUsersTable() {
        try(Statement stat = conn.createStatement()) {
            String str = "DROP TABLE IF EXISTS users";
            stat.executeUpdate(str);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
    try (PreparedStatement pstat = conn.prepareStatement("INSERT INTO users(name, lastName, age) VALUES (?,?,?)")){
    pstat.setString(1, name);
    pstat.setString(2, lastName);
    pstat.setByte(3, age);
        pstat.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pstat = conn.prepareStatement("DELETE FROM  users WHERE ID = ?")){
            pstat.setLong(1, id);
            pstat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement pstat = conn.prepareStatement("SELECT * FROM users")){
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("lastName"),rs.getByte("age") );
                user.setId(rs.getLong("ID"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement pstat = conn.prepareStatement("TRUNCATE TABLE users")){
            pstat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
