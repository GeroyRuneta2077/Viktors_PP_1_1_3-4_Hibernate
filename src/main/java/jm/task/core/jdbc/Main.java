package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl test = new UserServiceImpl();

        test.createUsersTable();
        test.saveUser("Ivan", "Ivanov", (byte) 22);
        test.saveUser("Billy", "Idol", (byte) 70);
        test.saveUser("Ilya", "Rodin", (byte) 32);
        test.saveUser("Alexander", "Konstantinov", (byte) 25);
        System.out.println(test.getAllUsers());
        test.cleanUsersTable();
        test.dropUsersTable();
    }
}
