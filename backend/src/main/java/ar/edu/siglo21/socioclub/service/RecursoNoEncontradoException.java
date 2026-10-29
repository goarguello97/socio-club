package ar.edu.siglo21.socioclub.service;

/** Se lanza cuando se busca un socio, actividad o inscripción que no existe (404 en la API). */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
