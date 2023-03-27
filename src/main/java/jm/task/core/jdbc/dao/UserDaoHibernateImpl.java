package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users "+
                "(id BIGINT PRIMARY KEY auto_increment, " +
                "Name TINYTEXT NOT NULL, " +
                "LastName TINYTEXT NOT NULL, " +
                "Age TINYINT NOT NULL)";
        SQLQuery(sql);
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        SQLQuery(sql);
    }

    private void SQLQuery(String sql) {
        Session session = Util.getSession();

        try {
            session.beginTransaction();

            session.createSQLQuery(sql).executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Exception during C/D operation: " + e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSession();

        try {
            session.beginTransaction();

            session.save(new User(name, lastName, age));

            session.getTransaction().commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Saving failed, reason: " + e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession();

        try {
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Deleting failed, reason: " + e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = null;
        Session session = Util.getSession();

        try {
            session.beginTransaction();

            result = session.createQuery("FROM User").getResultList();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();

        try {
            session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Exception during deleting: " + e);
        }
    }
}
