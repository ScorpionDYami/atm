package mx.itson.atm.entities;
/**
 *
 * @author Rayan
 */

import javax.persistence.*;

@Entity
@Table(name = "tarjeta")
public class Tarjeta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String numero;

    @Column(nullable = false)
    private String nip;
    
    @Column(name = "intentos_fallidos")
    private int intentosFallidos;

    @Column(name = "bloqueada")
    private boolean bloqueada;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    public Tarjeta() {}

    public Tarjeta(String numero, String nip, Cuenta cuenta) {
        this.numero = numero;
        this.nip = nip;
        this.cuenta = cuenta;
    }

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    /**
     * @return the intentosFallidos
     */
    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    /**
     * @param intentosFallidos the intentosFallidos to set
     */
    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    /**
     * @return the bloqueada
     */
    public boolean isBloqueada() {
        return bloqueada;
    }

    /**
     * @param bloqueada the bloqueada to set
     */
    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }
}

