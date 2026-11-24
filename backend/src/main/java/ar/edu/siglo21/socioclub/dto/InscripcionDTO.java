package ar.edu.siglo21.socioclub.dto;

import java.time.LocalDate;

import ar.edu.siglo21.socioclub.model.Inscripcion;

/**
 * Una inscripción tal como la ve la app: datos propios, el id del socio y la actividad completa.
 * No incluye el objeto Socio entero: así se corta el ciclo Socio -> Inscripcion -> Socio.
 */
public class InscripcionDTO {

    private Long id;
    private LocalDate fechaInscripcion;
    private String estado;
    private Long socioId;
    private ActividadDTO actividad;

    public static InscripcionDTO desde(Inscripcion i) {
        InscripcionDTO dto = new InscripcionDTO();
        dto.id = i.getId();
        dto.fechaInscripcion = i.getFechaInscripcion();
        dto.estado = i.getEstado().name();
        dto.socioId = i.getSocio().getId();
        dto.actividad = ActividadDTO.desde(i.getActividad());
        return dto;
    }

    public Long getId() { return id; }
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public String getEstado() { return estado; }
    public Long getSocioId() { return socioId; }
    public ActividadDTO getActividad() { return actividad; }
}
