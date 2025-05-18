
package mx.itson.atm.services;

import mx.itson.atm.entities.Tarjeta;
import org.mindrot.jbcrypt.BCrypt;

public class Autenticador {

    public boolean validar(Tarjeta tarjeta, String nipIngresado) {
        String nipTexto = String.valueOf(nipIngresado);
        String nipGuardadoEncriptado = tarjeta.getNip();

        return BCrypt.checkpw(nipTexto, nipGuardadoEncriptado);
    }
}
