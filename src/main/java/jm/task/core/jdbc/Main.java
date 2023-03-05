package jm.task.core.jdbc;

//import static jm.task.core.jdbc.util.Util.connectDB;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Walge", "Leonsson", (byte) 32);
        userService.saveUser("Howard", "Lovecraft", (byte) 46);
        userService.saveUser("Arthur", "Machen", (byte) 84);
        System.out.println(userService.getAllUsers().toString());
        userService.removeUserById(1);
        System.out.println(userService.getAllUsers().toString());
        userService.cleanUsersTable();
        System.out.println(userService.getAllUsers().toString());
        userService.dropUsersTable(); //реализуйте алгоритм здесь
    }
}
