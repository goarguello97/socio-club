package ar.edu.siglo21.socioclub.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.siglo21.socioclub.model.Actividad;
import ar.edu.siglo21.socioclub.model.EstadoCuota;
import ar.edu.siglo21.socioclub.model.EstadoInscripcion;
import ar.edu.siglo21.socioclub.model.Inscripcion;
import ar.edu.siglo21.socioclub.model.Socio;
import ar.edu.siglo21.socioclub.repository.ActividadRepository;
import ar.edu.siglo21.socioclub.repository.InscripcionRepository;
import ar.edu.siglo21.socioclub.repository.SocioRepository;

/**
 * Inscripciones de socios a actividades. Acá viven las reglas de negocio RN1 a RN5:
 * tanto el sistema de secretaría (Thymeleaf) como la API REST llaman a este mismo método,
 * así que una inscripción se valida igual entre por donde entre.
 */
@Service
public class InscripcionService {

    /** RN5: máximo de actividades activas por socio. Cambiar este número cambia la regla en todo el sistema. */
    public static final int MAXIMO_ACTIVIDADES = 3;

    private final InscripcionRepository inscripcionRepository;
    private final SocioRepository socioRepository;
    private final ActividadRepository actividadRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository, SocioRepository socioRepository,
                              ActividadRepository actividadRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.socioRepository = socioRepository;
        this.actividadRepository = actividadRepository;
    }

    /** Inscribe un socio a una actividad aplicando las reglas en el orden de la consigna (RF6). */
    @Transactional
    public Inscripcion inscribir(Long socioId, Long actividadId) {
        Socio socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el socio con id " + socioId));
        Actividad actividad = actividadRepository.findById(actividadId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la actividad con id " + actividadId));

        // RN1: cupo
        long activasEnActividad = inscripcionRepository.contarActivasPorActividad(actividadId);
        if (activasEnActividad >= actividad.getCupoMaximo()) {
            throw new ReglaNegocioException("RN1", "La actividad no tiene cupo disponible");
        }

        // RN2: sin duplicados
        if (inscripcionRepository.existsBySocioIdAndActividadIdAndEstado(socioId, actividadId, EstadoInscripcion.ACTIVA)) {
            throw new ReglaNegocioException("RN2", "El socio ya está inscripto en esta actividad");
        }

        // RN3: edad dentro del rango de la actividad
        int edad = socio.getEdad();
        if (!actividad.esAptaParaEdad(edad)) {
            throw new ReglaNegocioException("RN3", "La actividad es para edades de " + actividad.getEdadMinima()
                    + " a " + actividad.getEdadMaxima() + " años y el socio tiene " + edad);
        }

        // RN4: cuota al día
        if (socio.getEstadoCuota() == EstadoCuota.MOROSO) {
            throw new ReglaNegocioException("RN4", "Debe regularizar su cuota en secretaría");
        }

        // RN5: máximo de actividades
        long activasDelSocio = inscripcionRepository.countBySocioIdAndEstado(socioId, EstadoInscripcion.ACTIVA);
        if (activasDelSocio >= MAXIMO_ACTIVIDADES) {
            throw new ReglaNegocioException("RN5", "Alcanzó el máximo de " + MAXIMO_ACTIVIDADES + " actividades");
        }

        Inscripcion guardada = inscripcionRepository.save(new Inscripcion(socio, actividad));
        completarCupoDisponible(guardada.getActividad());
        return guardada;
    }

    /** Baja lógica: la inscripción no se borra, pasa a estado BAJA (RF9). */
    @Transactional
    public Inscripcion darDeBaja(Long inscripcionId) {
        Inscripcion inscripcion = buscarPorId(inscripcionId);
        inscripcion.setEstado(EstadoInscripcion.BAJA);
        Inscripcion guardada = inscripcionRepository.save(inscripcion);
        completarCupoDisponible(guardada.getActividad());
        return guardada;
    }

    @Transactional(readOnly = true)
    public Inscripcion buscarPorId(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la inscripción con id " + id));
    }

    /** Inscripciones ACTIVAS de un socio (RF8). Si el socio no existe, lanza 404. */
    @Transactional(readOnly = true)
    public List<Inscripcion> listarActivasDeSocio(Long socioId) {
        if (!socioRepository.existsById(socioId)) {
            throw new RecursoNoEncontradoException("No existe el socio con id " + socioId);
        }
        List<Inscripcion> activas = inscripcionRepository.findBySocioIdAndEstado(socioId, EstadoInscripcion.ACTIVA);
        for (Inscripcion inscripcion : activas) {
            completarCupoDisponible(inscripcion.getActividad());
        }
        return activas;
    }

    /** Inscriptos ACTIVOS de una actividad, ordenados por apellido (RF7). */
    @Transactional(readOnly = true)
    public List<Inscripcion> listarActivasDeActividad(Long actividadId) {
        if (!actividadRepository.existsById(actividadId)) {
            throw new RecursoNoEncontradoException("No existe la actividad con id " + actividadId);
        }
        return inscripcionRepository.findByActividadIdAndEstadoOrderBySocioApellidoAsc(actividadId, EstadoInscripcion.ACTIVA);
    }

    /** El cupo disponible no se guarda en la base: se calcula con las inscripciones activas. */
    private void completarCupoDisponible(Actividad actividad) {
        long activas = inscripcionRepository.contarActivasPorActividad(actividad.getId());
        actividad.setCupoDisponible(actividad.getCupoMaximo() - (int) activas);
    }
}
