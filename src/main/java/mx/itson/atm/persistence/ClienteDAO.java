
package mx.itson.atm.persistence;

import mx.itson.atm.entities.Cliente;
import org.hibernate.Session;

import java.util.List;

public class ClienteDAO {

    public void guardar(Session session, Cliente cliente) {
        session.save(cliente);
    }

    public Cliente obtenerPorId(Session session, int id) {
        return session.get(Cliente.class, id);
    }

    public List<Cliente> listarTodos(Session session) {
        return session.createQuery("FROM Cliente", Cliente.class).list();
    }
}
