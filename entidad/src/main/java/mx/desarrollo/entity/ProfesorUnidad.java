package mx.desarrollo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "profesor_unidad")
public class ProfesorUnidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion")
    private int idAsignacion;

    @ManyToOne
    @JoinColumn(name = "id_profesor", nullable = false)
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "id_unidad", nullable = false)
    private UnidadAprendizaje unidadAprendizaje;

    @Column(name = "dia", nullable = false)
    private String dia;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    public ProfesorUnidad() {}

    // Getters y Setters
    public int getIdAsignacion() { return idAsignacion; }
    public void setIdAsignacion(int idAsignacion) { this.idAsignacion = idAsignacion; }

    public Profesor getProfesor() { return profesor; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }

    public UnidadAprendizaje getUnidadAprendizaje() { return unidadAprendizaje; }
    public void setUnidadAprendizaje(UnidadAprendizaje unidadAprendizaje) { this.unidadAprendizaje = unidadAprendizaje; }

    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
}