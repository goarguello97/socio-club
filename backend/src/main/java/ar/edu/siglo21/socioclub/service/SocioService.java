package ar.edu.siglo21.socioclub.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.siglo21.socioclub.model.EstadoCuota;
import ar.edu.siglo21.socioclub.model.Socio;
import ar.edu.siglo21.socioclub.repository.SocioRepository;

/**
 * Lógica de negocio de los socios: listado paginado, búsqueda y alta/modificación.
 */
@Service
public class SocioService {

    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    /** Listado paginado; si viene un apellido, filtra por "contiene" sin distinguir mayúsculas (RF5). */
    @Transactional(readOnly = true)
    public Page<Socio> listar(String apellido, Pageable pageable) {
        String texto = apellido == null ? "" : apellido.trim();
        return socioRepository.findByApellidoContainingIgnoreCase(texto, pageable);
    }

    /** Todos los socios ordenados por apellido (para elegir uno en el formulario de inscripción). */
    @Transactional(readOnly = true)
    public List<Socio> listarTodos() {
        return socioRepository.findAll(Sort.by("apellido", "nombre"));
    }

    @Transactional(readOnly = true)
    public Socio buscarPorId(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el socio con id " + id));
    }

    /** Ingreso a la app: busca un socio por su DNI (RF8). */
    @Transactional(readOnly = true)
    public Socio buscarPorDni(String dni) {
        return socioRepository.findByDni(dni)
                .orElseThrow(() -> new RecursoNoEncontradoException("No hay ningún socio con el DNI " + dni));
    }

    /** Alta (id nulo) o modificación (id existente) de un socio. */
    @Transactional
    public Socio guardar(Socio datos) {
        validar(datos);

        Socio socio;
        if (datos.getId() == null) {
            if (socioRepository.existsByDni(datos.getDni())) {
                throw new ReglaNegocioException("DNI_REPETIDO", "Ya existe un socio con el DNI " + datos.getDni());
            }
            socio = new Socio();
        } else {
            socio = buscarPorId(datos.getId());
            boolean cambioDni = !socio.getDni().equals(datos.getDni());
            if (cambioDni && socioRepository.existsByDni(datos.getDni())) {
                throw new ReglaNegocioException("DNI_REPETIDO", "Ya existe un socio con el DNI " + datos.getDni());
            }
        }

        socio.setDni(datos.getDni());
        socio.setNombre(datos.getNombre().trim());
        socio.setApellido(datos.getApellido().trim());
        socio.setFechaNacimiento(datos.getFechaNacimiento());
        socio.setEmail(vacioANulo(datos.getEmail()));
        socio.setTelefono(vacioANulo(datos.getTelefono()));
        socio.setEstadoCuota(datos.getEstadoCuota() == null ? EstadoCuota.AL_DIA : datos.getEstadoCuota());
        return socioRepository.save(socio);
    }

    private void validar(Socio s) {
        if (estaVacio(s.getNombre()) || estaVacio(s.getApellido())) {
            throw new ReglaNegocioException("DATOS", "Nombre y apellido son obligatorios");
        }
        // RN7 también se controla en el servidor: el DNI no puede llegar con puntos ni letras
        if (s.getDni() == null || !s.getDni().matches("\\d{7,8}")) {
            throw new ReglaNegocioException("RN7", "El DNI debe tener 7 u 8 dígitos numéricos, sin puntos");
        }
        if (s.getFechaNacimiento() == null) {
            throw new ReglaNegocioException("DATOS", "La fecha de nacimiento es obligatoria");
        }
        if (s.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new ReglaNegocioException("DATOS", "La fecha de nacimiento no puede ser futura");
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    private String vacioANulo(String texto) {
        return estaVacio(texto) ? null : texto.trim();
    }
}
