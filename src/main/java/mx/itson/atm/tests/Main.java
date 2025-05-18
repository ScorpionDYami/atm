
package mx.itson.atm.tests;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import mx.itson.atm.business.ATM;
import mx.itson.atm.entities.Cuenta;
import mx.itson.atm.entities.Tarjeta;
import mx.itson.atm.entities.Transaccion;
import mx.itson.atm.persistence.TarjetaDAO;
import mx.itson.atm.persistence.TransaccionDAO;
import mx.itson.atm.utils.HibernateUtil;
import mx.itson.atm.utils.SessionManager;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SessionManager sessionManager = new SessionManager();
        TarjetaDAO tarjetaDAO = new TarjetaDAO();
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        ATM atm = new ATM();

        try {
            sessionManager.openSession();
            Session session = sessionManager.getSession();

            System.out.println("=== Bienvenido al Cajero Automático ===");
            System.out.print("Ingrese número de tarjeta: ");
            String numeroTarjeta = scanner.nextLine();

            Tarjeta tarjeta = tarjetaDAO.buscarPorNumero(session, numeroTarjeta);

            if (tarjeta == null) {
                System.out.println("Tarjeta no válida.");
                return;
            }

            System.out.print("Ingrese su NIP: ");
            String nipIngresado = scanner.nextLine();
            

            if (!atm.autenticar(tarjeta, nipIngresado)) {
                System.out.println("NIP incorrecto.");
                return;
            }

            Cuenta cuenta = tarjeta.getCuenta();
            System.out.println("✅ Bienvenido, " + cuenta.getCliente().getNombre());

            boolean salir = false;

            while (!salir) {
                try {
                    System.out.println("\n=== MENÚ DE TRANSACCIONES ===");
                    System.out.println("1. Consultar saldo");
                    System.out.println("2. Retirar efectivo");
                    System.out.println("3. Depositar efectivo");
                    System.out.println("4. Ver últimos movimientos");
                    System.out.println("5. Transferencia");
                    System.out.println("0. Salir");
                    System.out.print("Seleccione una opción: ");
                    int opcion = scanner.nextInt();

                    switch (opcion) {
                        case 1:
                            atm.realizarConsulta(cuenta);
                            break;

                        case 2:
                            System.out.print("Ingrese monto a retirar: ");
                            double retiro = scanner.nextDouble();
                            if (atm.realizarRetiro(session, cuenta, retiro)) {
                                System.out.println("✅ Retiro exitoso.");
                            } else {
                                System.out.println("❌ Fondos insuficientes.");
                            }
                            break;

                        case 3:
                            System.out.print("Ingrese monto a depositar: ");
                            double deposito = scanner.nextDouble();
                            atm.realizarDeposito(session, cuenta, deposito);
                            System.out.println("✅ Depósito exitoso.");
                            break;

                        case 4:
                            System.out.println("🧾 Últimas transacciones:");
                            List<Transaccion> transacciones = transaccionDAO.listarPorCuentaId(session, cuenta.getId());
                            for (Transaccion t : transacciones) {
                                System.out.printf("Tipo: %s | Monto: %.2f | Fecha: %s\n",
                                        t.getTipo(), t.getMonto(), t.getFechaHora());
                            }
                            break;

                        case 5:
                            System.out.print("Ingrese número de tarjeta destino: ");
                            scanner.nextLine();
                            String tarjetaDestinoNumero = scanner.nextLine();

                            Tarjeta tarjetaDestino = tarjetaDAO.buscarPorNumero(session, tarjetaDestinoNumero);
                            if (tarjetaDestino == null) {
                                System.out.println("❌ Cuenta destino no encontrada.");
                                break;
                            }

                            Cuenta cuentaDestino = tarjetaDestino.getCuenta();
                            if (cuenta.getId() == cuentaDestino.getId()) {
                                System.out.println("❌ No puedes transferir a tu misma cuenta.");
                                break;
                            }

                            System.out.print("Ingrese monto a transferir: ");
                            double montoTransferencia = scanner.nextDouble();

                            if (atm.realizarTransferencia(session, cuenta, cuentaDestino, montoTransferencia)) {
                                System.out.println("✅ Transferencia realizada con éxito.");
                            } else {
                                System.out.println("❌ La transferencia no se pudo completar.");
                            }
                            break;

                        case 0:
                            salir = true;
                            System.out.println("👋 Gracias por usar el cajero. Hasta pronto.");
                            break;

                        default:
                            System.out.println("⚠️ Opción inválida.");
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Ocurrió un error durante la transacción: " + e.getMessage());
                    scanner.nextLine(); // limpiar entrada para evitar ciclo infinito si hay error de tipo
                }
            }
        } catch (Exception ex) {
            System.out.println("❌ Error inesperado: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            sessionManager.closeSession();
            HibernateUtil.shutdown();
            scanner.close();
        }
    }
}
