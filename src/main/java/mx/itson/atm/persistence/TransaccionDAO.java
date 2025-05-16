package mx.itson.atm.persistence;
/**
 *
 * @author Rayan
 */
import mx.itson.atm.entities.Transaccion;
import org.hibernate.Session;

import java.util.List;

public class TransaccionDAO {

    public void guardar(Session session, Transaccion transaccion) {
        session.save(transaccion);
    }

    public List<Transaccion> listarPorCuentaId(Session session, int cuentaId) {
        return session.createQuery(
                "FROM Transaccion WHERE cuenta.id = :cuentaId ORDER BY fechaHora DESC",
                Transaccion.class)
                .setParameter("cuentaId", cuentaId)
                .list();
    }
}

