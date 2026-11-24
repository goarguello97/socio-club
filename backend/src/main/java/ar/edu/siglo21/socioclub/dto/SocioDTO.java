package ar.edu.siglo21.socioclub.dto;

import java.time.LocalDate;

import ar.edu.siglo21.socioclub.model.Socio;

/** Datos del socio que viajan a la app (sin la lista de inscripciones, para evitar el ciclo en el JSON). */
public class SocioDTO {

    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private int edad;
    private String email;
    private String telefono;
    private String estadoCuota;

    public static SocioDTO desde(Socio s) {
        SocioDTO dto = new SocioDTO();
        dto.id = s.getId();
        dto.dni = s.getDni();
        dto.nombre = s.getNombre();
        dto.apellido = s.getApellido();
        dto.fechaNacimiento = s.getFechaNacimiento();
        dto.edad = s.getEdad();
        dto.email = s.getEmail();
        dto.telefono = s.getTelefono();
        dto.estadoCuota = s.getEstadoCuota().name();
        return dto;
    }

    public Long getId() { return id; }
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public int getEdad() { return edad; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getEstadoCuota() { return estadoCuota; }
}
