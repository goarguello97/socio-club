package ar.edu.siglo21.socioclub.dto;

import java.math.BigDecimal;

import ar.edu.siglo21.socioclub.model.Actividad;

/**
 * Lo que la API devuelve de una actividad. Es una clase aparte de la entidad para que el JSON
 * no incluya la lista de inscripciones (y así no se arma el ciclo Actividad -> Inscripcion -> Actividad).
 */
public class ActividadDTO {

    private Long id;
    private String nombre;
    private String disciplina;
    private String profesor;
    private String dias;
    private String horario;
    private Integer edadMinima;
    private Integer edadMaxima;
    private Integer cupoMaximo;
    private Integer cupoDisponible;
    private BigDecimal cuotaMensual;

    public ActividadDTO() {
    }

    public static ActividadDTO desde(Actividad a) {
        ActividadDTO dto = new ActividadDTO();
        dto.id = a.getId();
        dto.nombre = a.getNombre();
        dto.disciplina = a.getDisciplina();
        dto.profesor = a.getProfesor();
        dto.dias = a.getDias();
        dto.horario = a.getHorario();
        dto.edadMinima = a.getEdadMinima();
        dto.edadMaxima = a.getEdadMaxima();
        dto.cupoMaximo = a.getCupoMaximo();
        dto.cupoDisponible = a.getCupoDisponible();
        dto.cuotaMensual = a.getCuotaMensual();
        return dto;
    }

    /** Convierte el JSON recibido (POST/PUT) en una entidad para pasarle al servicio. */
    public Actividad aEntidad() {
        Actividad a = new Actividad();
        a.setNombre(nombre);
        a.setDisciplina(disciplina);
        a.setProfesor(profesor);
        a.setDias(dias);
        a.setHorario(horario);
        a.setEdadMinima(edadMinima);
        a.setEdadMaxima(edadMaxima);
        a.setCupoMaximo(cupoMaximo);
        a.setCuotaMensual(cuotaMensual);
        return a;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }
    public String getProfesor() { return profesor; }
    public void setProfesor(String profesor) { this.profesor = profesor; }
    public String getDias() { return dias; }
    public void setDias(String dias) { this.dias = dias; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    public Integer getEdadMinima() { return edadMinima; }
    public void setEdadMinima(Integer edadMinima) { this.edadMinima = edadMinima; }
    public Integer getEdadMaxima() { return edadMaxima; }
    public void setEdadMaxima(Integer edadMaxima) { this.edadMaxima = edadMaxima; }
    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer cupoMaximo) { this.cupoMaximo = cupoMaximo; }
    public Integer getCupoDisponible() { return cupoDisponible; }
    public void setCupoDisponible(Integer cupoDisponible) { this.cupoDisponible = cupoDisponible; }
    public BigDecimal getCuotaMensual() { return cuotaMensual; }
    public void setCuotaMensual(BigDecimal cuotaMensual) { this.cuotaMensual = cuotaMensual; }
}
