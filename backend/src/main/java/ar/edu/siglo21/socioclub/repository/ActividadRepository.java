package ar.edu.siglo21.socioclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.siglo21.socioclub.model.Actividad;

/**
 * Al extender JpaRepository ya tenemos findAll, findById, save, deleteById, count, etc.
 * Spring genera la implementación en tiempo de ejecución.
 */
public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    // Método derivado del nombre: SELECT ... FROM actividades ORDER BY nombre
    List<Actividad> findAllByOrderByNombreAsc();

    // Método derivado del nombre: SELECT ... FROM actividades WHERE disciplina = ? ORDER BY nombre
    List<Actividad> findByDisciplinaOrderByNombreAsc(String disciplina);

    // Consulta JPQL escrita a mano: lista de disciplinas sin repetir (para el filtro)
    @Query("SELECT DISTINCT a.disciplina FROM Actividad a ORDER BY a.disciplina")
    List<String> findDisciplinas();
}
