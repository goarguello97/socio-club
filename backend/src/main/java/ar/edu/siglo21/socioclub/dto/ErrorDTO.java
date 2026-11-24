package ar.edu.siglo21.socioclub.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/** Cuerpo de las respuestas de error: { "error": "La actividad no tiene cupo disponible", "regla": "RN1" } */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {

    private final String error;
    private final String regla;

    public ErrorDTO(String error, String regla) {
        this.error = error;
        this.regla = regla;
    }

    public String getError() { return error; }
    public String getRegla() { return regla; }
}
