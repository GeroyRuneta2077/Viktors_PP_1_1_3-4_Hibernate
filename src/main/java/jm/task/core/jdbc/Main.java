package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

        service.createUsersTable();
        service.saveUser("Ivan", "Ivanov", (byte) 22);
        service.saveUser("Billy", "Idol", (byte) 70);
        service.saveUser("Ilya", "Rodin", (byte) 32);
        service.saveUser("Alexander", "Konstantinov", (byte) 25);
        System.out.println(service.getAllUsers());
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
