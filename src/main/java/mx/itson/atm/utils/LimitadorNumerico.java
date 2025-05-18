
package mx.itson.atm.utils;

import javax.swing.text.*;

public class LimitadorNumerico extends DocumentFilter {

    private int limite;

    public LimitadorNumerico(int limite) {
        this.limite = limite;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (esValido(string, fb)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)
            throws BadLocationException {
        if (esValido(string, fb)) {
            super.replace(fb, offset, length, string, attr);
        }
    }

    private boolean esValido(String texto, FilterBypass fb) {
        // Solo permitir si es numérico y no excede el límite
        String nuevoTexto = fb.getDocument().toString() + texto;
        return texto.matches("\\d*") && (fb.getDocument().getLength() + texto.length() <= limite);
    }
}
