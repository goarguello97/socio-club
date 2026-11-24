package ar.edu.siglo21.socioclub.dto;

/** Cuerpo de POST /api/inscripciones: { "socioId": 3, "actividadId": 1 } */
public class InscripcionRequest {

    private Long socioId;
    private Long actividadId;

    public Long getSocioId() { return socioId; }
    public void setSocioId(Long socioId) { this.socioId = socioId; }
    public Long getActividadId() { return actividadId; }
    public void setActividadId(Long actividadId) { this.actividadId = actividadId; }
}
