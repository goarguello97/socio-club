package ar.edu.siglo21.socioclub.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.siglo21.socioclub.model.Socio;

public interface SocioRepository extends JpaRepository<Socio, Long> {

    // WHERE UPPER(apellido) LIKE UPPER('%texto%'), paginado (RF5)
    Page<Socio> findByApellidoContainingIgnoreCase(String apellido, Pageable pageable);

    // WHERE dni = ? (ingreso del socio a la app, RF8)
    Optional<Socio> findByDni(String dni);

    boolean existsByDni(String dni);
}
