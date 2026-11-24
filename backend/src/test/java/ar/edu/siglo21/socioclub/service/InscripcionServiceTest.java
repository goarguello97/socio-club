package ar.edu.siglo21.socioclub.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.siglo21.socioclub.model.Actividad;
import ar.edu.siglo21.socioclub.model.EstadoCuota;
import ar.edu.siglo21.socioclub.model.EstadoInscripcion;
import ar.edu.siglo21.socioclub.model.Inscripcion;
import ar.edu.siglo21.socioclub.model.Socio;
import ar.edu.siglo21.socioclub.repository.ActividadRepository;
import ar.edu.siglo21.socioclub.repository.InscripcionRepository;
import ar.edu.siglo21.socioclub.repository.SocioRepository;

/**
 * Pruebas unitarias de las reglas RN1 a RN5. Los repositorios se reemplazan por "mocks" de Mockito,
 * así que no hace falta la base de datos: cada prueba arma la situación que quiere probar.
 */
class InscripcionServiceTest {

    private InscripcionRepository inscripcionRepository;
    private SocioRepository socioRepository;
    private ActividadRepository actividadRepository;
    private InscripcionService servicio;

    private Socio socio;
    private Actividad yoga;

    @BeforeEach
    void preparar() {
        inscripcionRepository = mock(InscripcionRepository.class);
        socioRepository = mock(SocioRepository.class);
        actividadRepository = mock(ActividadRepository.class);
        servicio = new InscripcionService(inscripcionRepository, socioRepository, actividadRepository);

        socio = new Socio();
        socio.setId(1L);
        socio.setNombre("Nicolás");
        socio.setApellido("Vega");
        socio.setDni("45901234");
        socio.setFechaNacimiento(LocalDate.now().minusYears(22));
        socio.setEstadoCuota(EstadoCuota.AL_DIA);

        yoga = new Actividad();
        yoga.setId(8L);
        yoga.setNombre("Yoga");
        yoga.setEdadMinima(16);
        yoga.setEdadMaxima(99);
        yoga.setCupoMaximo(15);
        yoga.setCuotaMensual(new BigDecimal("17000"));

        when(socioRepository.findById(1L)).thenReturn(Optional.of(socio));
        when(actividadRepository.findById(8L)).thenReturn(Optional.of(yoga));
        when(inscripcionRepository.save(any(Inscripcion.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void inscribeCuandoSeCumplenTodasLasReglas() {
        when(inscripcionRepository.contarActivasPorActividad(8L)).thenReturn(14L);

        Inscripcion inscripcion = servicio.inscribir(1L, 8L);

        assertEquals(EstadoInscripcion.ACTIVA, inscripcion.getEstado());
        assertEquals(LocalDate.now(), inscripcion.getFechaInscripcion());
    }

    @Test
    void rn1RechazaSiLaActividadNoTieneCupo() {
        when(inscripcionRepository.contarActivasPorActividad(8L)).thenReturn(15L);

        ReglaNegocioException e = assertThrows(ReglaNegocioException.class, () -> servicio.inscribir(1L, 8L));

        assertEquals("RN1", e.getRegla());
        assertEquals("La actividad no tiene cupo disponible", e.getMessage());
        verify(inscripcionRepository, never()).save(any());
    }

    @Test
    void rn2RechazaSiElSocioYaEstaInscripto() {
        when(inscripcionRepository.existsBySocioIdAndActividadIdAndEstado(1L, 8L, EstadoInscripcion.ACTIVA)).thenReturn(true);

        ReglaNegocioException e = assertThrows(ReglaNegocioException.class, () -> servicio.inscribir(1L, 8L));

        assertEquals("RN2", e.getRegla());
    }

    @Test
    void rn3RechazaSiLaEdadEstaFueraDeRango() {
        socio.setFechaNacimiento(LocalDate.now().minusYears(14));

        ReglaNegocioException e = assertThrows(ReglaNegocioException.class, () -> servicio.inscribir(1L, 8L));

        assertEquals("RN3", e.getRegla());
        assertEquals("La actividad es para edades de 16 a 99 años y el socio tiene 14", e.getMessage());
    }

    @Test
    void rn4RechazaSiElSocioEsMoroso() {
        socio.setEstadoCuota(EstadoCuota.MOROSO);

        ReglaNegocioException e = assertThrows(ReglaNegocioException.class, () -> servicio.inscribir(1L, 8L));

        assertEquals("RN4", e.getRegla());
        assertEquals("Debe regularizar su cuota en secretaría", e.getMessage());
    }

    @Test
    void rn5RechazaSiYaTieneElMaximoDeActividades() {
        when(inscripcionRepository.countBySocioIdAndEstado(1L, EstadoInscripcion.ACTIVA))
                .thenReturn((long) InscripcionService.MAXIMO_ACTIVIDADES);

        ReglaNegocioException e = assertThrows(ReglaNegocioException.class, () -> servicio.inscribir(1L, 8L));

        assertEquals("RN5", e.getRegla());
        assertEquals("Alcanzó el máximo de 3 actividades", e.getMessage());
    }

    @Test
    void devuelve404SiElSocioNoExiste() {
        when(socioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> servicio.inscribir(99L, 8L));
    }

    @Test
    void laBajaEsLogica() {
        Inscripcion inscripcion = new Inscripcion(socio, yoga);
        when(inscripcionRepository.findById(5L)).thenReturn(Optional.of(inscripcion));

        Inscripcion resultado = servicio.darDeBaja(5L);

        assertEquals(EstadoInscripcion.BAJA, resultado.getEstado());
        verify(inscripcionRepository).save(inscripcion);
    }
}
