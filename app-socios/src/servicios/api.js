import axios from "axios";

/**
 * Toda la comunicación con el backend pasa por este archivo.
 * Los componentes nunca usan axios directamente: llaman a estas funciones.
 */
const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || "http://localhost:8080/api",
  timeout: 8000,
});

// GET /api/socios/dni/{dni} -> ingreso a la app
export function buscarSocioPorDni(dni) {
  return api.get(`/socios/dni/${dni}`).then((respuesta) => respuesta.data);
}

// GET /api/actividades?disciplina=X -> catálogo con cupo disponible
export function listarActividades(disciplina) {
  const params = disciplina ? { disciplina } : {};
  return api.get("/actividades", { params }).then((respuesta) => respuesta.data);
}

// GET /api/actividades/{id} -> detalle de una actividad
export function obtenerActividad(id) {
  return api.get(`/actividades/${id}`).then((respuesta) => respuesta.data);
}

// GET /api/socios/{id}/inscripciones -> inscripciones ACTIVAS del socio
export function listarInscripciones(socioId) {
  return api.get(`/socios/${socioId}/inscripciones`).then((respuesta) => respuesta.data);
}

// POST /api/inscripciones -> el backend aplica RN1 a RN5; si una falla responde 409
export function inscribir(socioId, actividadId) {
  return api.post("/inscripciones", { socioId, actividadId }).then((respuesta) => respuesta.data);
}

// PUT /api/inscripciones/{id}/baja -> baja lógica
export function darDeBaja(inscripcionId) {
  return api.put(`/inscripciones/${inscripcionId}/baja`).then((respuesta) => respuesta.data);
}

/**
 * Convierte un error de Axios en un mensaje para mostrarle al socio.
 * Si el backend mandó { "error": "...", "regla": "RN1" } (409 o 404), se usa ese texto.
 */
export function mensajeDeError(error) {
  if (error.response && error.response.data && error.response.data.error) {
    return error.response.data.error;
  }
  if (!error.response) {
    return "No pudimos conectarnos con el club. Revisá tu conexión e intentá de nuevo.";
  }
  return "Algo salió mal. Intentá de nuevo en unos minutos.";
}
