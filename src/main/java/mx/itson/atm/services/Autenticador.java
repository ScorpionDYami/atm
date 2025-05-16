
package mx.itson.atm.services;

import mx.itson.atm.entities.Tarjeta;

public class Autenticador {

    public boolean validar(Tarjeta tarjeta, int nipIngresado) {
        return tarjeta != null && tarjeta.getNip() == nipIngresado;
    }
}
