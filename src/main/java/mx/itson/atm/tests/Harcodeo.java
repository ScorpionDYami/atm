
package mx.itson.atm.tests;


import mx.itson.atm.entities.Cuenta;
import mx.itson.atm.entities.Tarjeta;
import mx.itson.atm.entities.Transaccion;
import mx.itson.atm.persistence.CuentaDAO;
import mx.itson.atm.persistence.TarjetaDAO;
import mx.itson.atm.utils.SessionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDateTime;
import mx.itson.atm.utils.HibernateUtil;

/**
 *
 * @author Kevin
 */
public class Harcodeo {

    private static SessionManager sessionManager = new SessionManager();
    private static TarjetaDAO tarjetaDAO = new TarjetaDAO();
    private static CuentaDAO cuentaDAO = new CuentaDAO();

    /*
    public static void main(String[] args) {

        sessionManager.openSession();
        Session session = sessionManager.getSession();
        Transaction tx = session.beginTransaction();

        try {
            // Obtener cuenta existente
            Cuenta cuenta = cuentaDAO.obtenerPorId(session, 1); // 👈 Asegúrate que la cuenta con ID 1 existe

            // Crear tarjeta
            Tarjeta tarjeta = new Tarjeta();
            tarjeta.setNumero("555 5555 5555 4444".replace(" ", "-")); // Formateo de número
            tarjeta.setNip(BCrypt.hashpw("1234", BCrypt.gensalt()));    // Encriptación del NIP
            tarjeta.setCuenta(cuenta);                                  // Asociación a la cuenta

            // Guardar tarjeta
            tarjetaDAO.guardar(session, tarjeta);

            tx.commit();
            System.out.println("✅ Tarjeta guardada correctamente");

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            sessionManager.closeSession();
        }
    }
    */
    
    
    
    public static void main(String[] args) {
        sessionManager.openSession();
        Session session = sessionManager.getSession();
        Transaction tx = session.beginTransaction();

        try {
            // Obtener la cuenta ya existente (por ID)
            Cuenta cuenta = session.get(Cuenta.class, 1); // Asegúrate que la cuenta con ID 1 exista

            if (cuenta == null) {
                System.out.println("❌ Cuenta no encontrada.");
                return;
            }

            // Crear la transacción
            Transaccion transaccion = new Transaccion();
            transaccion.setCuenta(cuenta);
            transaccion.setMonto(200.00); // depósito
            transaccion.setTipo("DEPOSITO");
            session.save(transaccion);

            // Actualizar el saldo de la cuenta
            cuenta.setSaldo(cuenta.getSaldo() + 200.00);
            session.update(cuenta);

            tx.commit();
            System.out.println("✅ Transacción de depósito registrada correctamente.");
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }
}
