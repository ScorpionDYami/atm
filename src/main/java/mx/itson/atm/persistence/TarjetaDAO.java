package mx.itson.atm.persistence;
/**
 *
 * @author Rayan
 */
import mx.itson.atm.entities.Tarjeta;
import org.hibernate.Session;

public class TarjetaDAO {

    public void guardar(Session session, Tarjeta tarjeta) {
        session.save(tarjeta);
    }

    public Tarjeta buscarPorNumero(Session session, String numero) {
        return session.createQuery(
                "FROM Tarjeta t JOIN FETCH t.cuenta c JOIN FETCH c.cliente WHERE t.numero = :numero",
                Tarjeta.class)
                .setParameter("numero", numero)
                .uniqueResult();
    }
}

