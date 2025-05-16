
package mx.itson.atm.persistence;

import mx.itson.atm.entities.Cuenta;
import org.hibernate.Session;

import java.util.List;

public class CuentaDAO {

    public void guardar(Session session, Cuenta cuenta) {
        session.save(cuenta);
    }

    public void actualizar(Session session, Cuenta cuenta) {
        session.update(cuenta);
    }

    public Cuenta obtenerPorId(Session session, int id) {
        return session.get(Cuenta.class, id);
    }

    public List<Cuenta> listarTodas(Session session) {
        return session.createQuery("FROM Cuenta", Cuenta.class).list();
    }
    
    public List<Cuenta> listarPorCliente(Session session, int clienteId) {
        return session.createQuery(
                "FROM Cuenta WHERE cliente.id = :clienteId", Cuenta.class)
                .setParameter("clienteId", clienteId)
                .list();
    }
}

