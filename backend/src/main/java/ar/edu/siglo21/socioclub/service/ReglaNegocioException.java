package ar.edu.siglo21.socioclub.service;

/**
 * Se lanza cuando una operación no cumple una regla de negocio (RN1 a RN5, u otras validaciones).
 * El controlador la atrapa y muestra el mensaje: en Thymeleaf como alerta, en la API como 409 Conflict.
 */
public class ReglaNegocioException extends RuntimeException {

    private final String regla;

    public ReglaNegocioException(String regla, String mensaje) {
        super(mensaje);
        this.regla = regla;
    }

    public String getRegla() {
        return regla;
    }
}
