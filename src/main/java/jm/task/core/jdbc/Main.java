package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Стив", "Стивов", (byte)36);
        userService.saveUser("Джон", "Джонов", (byte)66);
        userService.saveUser("Джейсон", "Джейсонов", (byte)56);
        userService.saveUser("Сильвестр", "Сильвестеров", (byte)46);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
        // реализуйте алгоритм здесь
    }
}