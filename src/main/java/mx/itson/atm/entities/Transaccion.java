package mx.itson.atm.entities;
/**
 *
 * @author Rayan
 */
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String tipo;  // RETIRO, DEPOSITO, CONSULTA.

    @Column(nullable = false)
    private double monto;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    public Transaccion() {}

    public Transaccion(String tipo, double monto, Cuenta cuenta) {
        this.tipo = tipo;
        this.monto = monto;
        this.cuenta = cuenta;
        this.fechaHora = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
