
package mx.itson.atm.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class SessionManager {
    private Session session;
    private Transaction transaction;

    public void openSession() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
    }

    public void beginTransaction() {
        if (session != null && transaction == null) {
            transaction = session.beginTransaction();
        }
    }

    public void commit() {
        if (transaction != null && !transaction.getRollbackOnly()) {
            transaction.commit();
        }
        transaction = null;
    }

    public void rollback() {
        if (transaction != null) {
            transaction.rollback();
        }
        transaction = null;
    }

    public void closeSession() {
        if (session != null && session.isOpen()) {
            session.close();
        }
        session = null;
        transaction = null;
    }

    public Session getSession() {
        return session;
    }
}
