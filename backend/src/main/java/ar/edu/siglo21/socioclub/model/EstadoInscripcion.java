package ar.edu.siglo21.socioclub.model;

/** Estado de una inscripción. La baja es lógica: la fila no se borra, pasa a BAJA (RF9). */
public enum EstadoInscripcion {
    ACTIVA,
    BAJA
}
