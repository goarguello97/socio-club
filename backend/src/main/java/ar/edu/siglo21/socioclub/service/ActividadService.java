package ar.edu.siglo21.socioclub.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.siglo21.socioclub.model.Actividad;
import ar.edu.siglo21.socioclub.repository.ActividadRepository;
import ar.edu.siglo21.socioclub.repository.InscripcionRepository;

/**
 * Lógica de negocio de las actividades. Los controladores (Thymeleaf y REST) solo hablan con esta
 * clase, nunca con los repositorios.
 */
@Service
public class ActividadService {

    private final ActividadRepository actividadRepository;
    private final InscripcionRepository inscripcionRepository;

    // Inyección por constructor: Spring crea los repositorios (beans) y los pasa acá
    public ActividadService(ActividadRepository actividadRepository, InscripcionRepository inscripcionRepository) {
        this.actividadRepository = actividadRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    /** Lista las actividades (todas o de una disciplina) con su cupo disponible calculado. */
    @Transactional(readOnly = true)
    public List<Actividad> listar(String disciplina) {
        List<Actividad> actividades;
        if (disciplina == null || disciplina.isBlank()) {
            actividades = actividadRepository.findAllByOrderByNombreAsc();
        } else {
            actividades = actividadRepository.findByDisciplinaOrderByNombreAsc(disciplina);
        }
        for (Actividad actividad : actividades) {
            completarCupoDisponible(actividad);
        }
        return actividades;
    }

    @Transactional(readOnly = true)
    public List<String> listarDisciplinas() {
        return actividadRepository.findDisciplinas();
    }

    @Transactional(readOnly = true)
    public Actividad buscarPorId(Long id) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la actividad con id " + id));
        completarCupoDisponible(actividad);
        return actividad;
    }

    /** Alta (id nulo) o modificación (id existente) de una actividad. */
    @Transactional
    public Actividad guardar(Actividad datos) {
        validar(datos);

        Actividad actividad;
        if (datos.getId() == null) {
            actividad = new Actividad();
        } else {
            actividad = actividadRepository.findById(datos.getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe la actividad con id " + datos.getId()));
            long activas = inscripcionRepository.contarActivasPorActividad(actividad.getId());
            if (datos.getCupoMaximo() < activas) {
                throw new ReglaNegocioException("RN1",
                        "El cupo no puede ser menor a los " + activas + " inscriptos activos");
            }
        }

        actividad.setNombre(datos.getNombre().trim());
        actividad.setDisciplina(datos.getDisciplina().trim());
        actividad.setProfesor(datos.getProfesor().trim());
        actividad.setDias(datos.getDias().trim());
        actividad.setHorario(datos.getHorario().trim());
        actividad.setEdadMinima(datos.getEdadMinima());
        actividad.setEdadMaxima(datos.getEdadMaxima());
        actividad.setCupoMaximo(datos.getCupoMaximo());
        actividad.setCuotaMensual(datos.getCuotaMensual());

        Actividad guardada = actividadRepository.save(actividad);
        completarCupoDisponible(guardada);
        return guardada;
    }

    /** Elimina una actividad solo si no tiene inscriptos activos. */
    @Transactional
    public void eliminar(Long id) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la actividad con id " + id));
        long activas = inscripcionRepository.contarActivasPorActividad(id);
        if (activas > 0) {
            throw new ReglaNegocioException("ACTIVIDAD_CON_INSCRIPTOS",
                    "No se puede eliminar la actividad porque tiene " + activas + " inscriptos activos");
        }
        actividadRepository.delete(actividad);
    }

    private void completarCupoDisponible(Actividad actividad) {
        long activas = inscripcionRepository.contarActivasPorActividad(actividad.getId());
        actividad.setCupoDisponible(actividad.getCupoMaximo() - (int) activas);
    }

    private void validar(Actividad a) {
        if (estaVacio(a.getNombre()) || estaVacio(a.getDisciplina()) || estaVacio(a.getProfesor())
                || estaVacio(a.getDias()) || estaVacio(a.getHorario())) {
            throw new ReglaNegocioException("DATOS", "Todos los campos de texto son obligatorios");
        }
        if (a.getEdadMinima() == null || a.getEdadMaxima() == null || a.getCupoMaximo() == null
                || a.getCuotaMensual() == null) {
            throw new ReglaNegocioException("DATOS", "Edades, cupo y cuota son obligatorios");
        }
        if (a.getEdadMinima() < 0 || a.getEdadMinima() > a.getEdadMaxima()) {
            throw new ReglaNegocioException("DATOS", "La edad mínima debe ser menor o igual a la edad máxima");
        }
        if (a.getCupoMaximo() < 1) {
            throw new ReglaNegocioException("DATOS", "El cupo máximo debe ser al menos 1");
        }
        if (a.getCuotaMensual().compareTo(BigDecimal.ZERO) < 0) {
            throw new ReglaNegocioException("DATOS", "La cuota mensual no puede ser negativa");
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }
}
