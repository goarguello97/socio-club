package ar.edu.siglo21.socioclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.siglo21.socioclub.model.EstadoInscripcion;
import ar.edu.siglo21.socioclub.model.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    // Cantidad de inscripciones ACTIVAS de una actividad (RN1 y cupo disponible)
    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE i.actividad.id = :actividadId "
            + "AND i.estado = ar.edu.siglo21.socioclub.model.EstadoInscripcion.ACTIVA")
    long contarActivasPorActividad(@Param("actividadId") Long actividadId);

    // Inscripciones de un socio en un estado (RF8, RN5)
    List<Inscripcion> findBySocioIdAndEstado(Long socioId, EstadoInscripcion estado);

    // Inscriptos de una actividad en un estado (RF7)
    List<Inscripcion> findByActividadIdAndEstadoOrderBySocioApellidoAsc(Long actividadId, EstadoInscripcion estado);

    // ¿El socio ya está inscripto en esta actividad? (RN2)
    boolean existsBySocioIdAndActividadIdAndEstado(Long socioId, Long actividadId, EstadoInscripcion estado);

    // Cantidad de actividades activas del socio (RN5)
    long countBySocioIdAndEstado(Long socioId, EstadoInscripcion estado);
}
