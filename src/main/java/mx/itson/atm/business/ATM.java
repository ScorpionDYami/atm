/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.business;

import mx.itson.atm.services.Autenticador;
import mx.itson.atm.persistence.CuentaDAO;
import mx.itson.atm.persistence.TransaccionDAO;
import mx.itson.atm.entities.Cuenta;
import mx.itson.atm.entities.Tarjeta;
import mx.itson.atm.entities.Transaccion;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ivan Arce
 */
public class ATM {
    
    private final Autenticador autenticador = new Autenticador();
    private final CuentaDAO cuentaDAO = new CuentaDAO();
    private final TransaccionDAO transaccionDAO = new TransaccionDAO();

    public boolean autenticar(Tarjeta tarjeta, String nip) {
        return autenticador.validar(tarjeta, nip);
    }

    public boolean realizarRetiro(Session session, Cuenta cuenta, double monto) {
        Transaction tx = session.beginTransaction();
        try {
            if (cuenta.getSaldo() >= monto) {
                cuenta.setSaldo(cuenta.getSaldo() - monto);
                cuentaDAO.actualizar(session, cuenta);

                Transaccion transaccion = new Transaccion("RETIRO", monto, cuenta);
                transaccionDAO.guardar(session, transaccion);

                tx.commit();
                return true;
            } else {
                System.out.println("Fondos insuficientes.");
                tx.rollback();
                return false;
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public void realizarDeposito(Session session, Cuenta cuenta, double monto) {
        Transaction tx = session.beginTransaction();
        try {
            cuenta.setSaldo(cuenta.getSaldo() + monto);
            cuentaDAO.actualizar(session, cuenta);

            Transaccion transaccion = new Transaccion("DEPOSITO", monto, cuenta);
            transaccionDAO.guardar(session, transaccion);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    public boolean realizarTransferencia(Session session, Cuenta origen, Cuenta destino, double monto) {
        Transaction tx = session.beginTransaction();
        try {
            if (origen.getId() == destino.getId()) {
                System.out.println("No puedes transferir a tu misma cuenta.");
                tx.rollback();
                return false;
            }

            if (origen.getSaldo() < monto) {
                System.out.println("Saldo insuficiente.");
                tx.rollback();
                return false;
            }

            origen.setSaldo(origen.getSaldo() - monto);
            cuentaDAO.actualizar(session, origen);
            transaccionDAO.guardar(session, new Transaccion("TRANSFERENCIA_ENVIO", monto, origen));

            
            destino.setSaldo(destino.getSaldo() + monto);
            cuentaDAO.actualizar(session, destino);
            transaccionDAO.guardar(session, new Transaccion("TRANSFERENCIA_RECIBE", monto, destino));

            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return false;
        }
    }


    public void realizarConsulta(Cuenta cuenta) {
        System.out.println("Saldo disponible: $" + cuenta.getSaldo());
    }
}
