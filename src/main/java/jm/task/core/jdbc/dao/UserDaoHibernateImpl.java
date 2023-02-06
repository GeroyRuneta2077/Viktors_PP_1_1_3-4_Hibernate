package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        SessionFactory sessionFactory = Util.createSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            session.createSQLQuery(sql).executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Exception during C/D operation: " + e);
        } finally {
            sessionFactory.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = Util.createSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            session.save(new User(name, lastName, age));

            session.getTransaction().commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Saving failed, reason: " + e);
        } finally {
            sessionFactory.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = Util.createSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Deleting failed, reason: " + e);
        } finally {
            sessionFactory.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = null;

        try (SessionFactory sessionFactory = Util.createSessionFactory()) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            result = session.createQuery("FROM User").getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.createSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Exception during deleting: " + e);
        } finally {
            sessionFactory.close();
        }
    }
}
