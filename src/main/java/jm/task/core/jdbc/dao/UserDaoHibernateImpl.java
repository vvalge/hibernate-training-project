package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory sessionFactory = Util.HibConnection();
    Session session = null;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL , " +
                    "lastname VARCHAR(100) NOT NULL , " +
                    "age TINYINT NOT NULL);";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table USERS created");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Table creation failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users;";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table USERS deleted");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Drop table failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User " + name + " " + lastName + " added");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("User adding failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
            System.out.println("User ID" + id + " deleted");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("User deletion failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String hql = "FROM User";
            users = session.createQuery(hql).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Getting a list of users failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            String hql = "DELETE User";
            session.createQuery(hql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table USERS cleaned up");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Table cleanup failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
