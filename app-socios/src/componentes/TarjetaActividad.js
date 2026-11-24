import PropTypes from "prop-types";
import "./TarjetaActividad.css";

/** Tarjeta de una actividad del catálogo. Recibe todo por props: no pide datos ni guarda estado. */
function TarjetaActividad({ actividad, onElegir, esParaMiEdad = true }) {
  const sinCupo = actividad.cupoDisponible === 0;

  // Clase aplicada según el estado: la tarjeta se apaga cuando no hay lugar
  const clases = ["tarjeta"];
  if (sinCupo) {
    clases.push("tarjeta--sin-cupo");
  }

  return (
    <article className={clases.join(" ")}>
      <header className="tarjeta__encabezado">
        <h2 className="tarjeta__nombre">{actividad.nombre}</h2>
        {sinCupo && <span className="tarjeta__etiqueta">Sin cupo</span>}
      </header>

      <dl className="tarjeta__datos">
        <dt>Días</dt>
        <dd>
          {actividad.dias} · {actividad.horario}
        </dd>
        <dt>Edades</dt>
        <dd>
          {actividad.edadMinima} a {actividad.edadMaxima} años
        </dd>
        <dt>Profe</dt>
        <dd>{actividad.profesor}</dd>
        <dt>Lugares</dt>
        <dd>
          {actividad.cupoDisponible} de {actividad.cupoMaximo}
        </dd>
      </dl>

      {!esParaMiEdad && <p className="tarjeta__aviso">No corresponde a tu edad</p>}

      <footer className="tarjeta__pie">
        <p className="tarjeta__cuota">
          $ {Number(actividad.cuotaMensual).toLocaleString("es-AR")} <small>por mes</small>
        </p>
        <button type="button" className="boton" disabled={sinCupo} onClick={() => onElegir(actividad.id)}>
          Inscribirme
        </button>
      </footer>
    </article>
  );
}

TarjetaActividad.propTypes = {
  actividad: PropTypes.shape({
    id: PropTypes.number.isRequired,
    nombre: PropTypes.string.isRequired,
    dias: PropTypes.string.isRequired,
    horario: PropTypes.string.isRequired,
    edadMinima: PropTypes.number.isRequired,
    edadMaxima: PropTypes.number.isRequired,
    profesor: PropTypes.string.isRequired,
    cupoMaximo: PropTypes.number.isRequired,
    cupoDisponible: PropTypes.number.isRequired,
    cuotaMensual: PropTypes.oneOfType([PropTypes.number, PropTypes.string]).isRequired,
  }).isRequired,
  onElegir: PropTypes.func.isRequired,
  esParaMiEdad: PropTypes.bool,
};

export default TarjetaActividad;
