import PropTypes from "prop-types";
import { calcularCuotaMensual, porcentajeDescuento } from "../utils/calcularCuotaMensual";

function formatear(pesos) {
  return "$ " + Number(pesos).toLocaleString("es-AR");
}

/** Cuota mensual estimada del socio según sus actividades activas (RN6). */
function ResumenCuota({ actividades }) {
  const subtotal = actividades.reduce((suma, a) => suma + Number(a.cuotaMensual), 0);
  const porcentaje = porcentajeDescuento(actividades.length);
  const total = calcularCuotaMensual(actividades);

  return (
    <section className="resumen" aria-labelledby="titulo-resumen">
      <h2 id="titulo-resumen">Tu cuota mensual estimada</h2>
      <dl className="resumen__lineas">
        <dt>Suma de cuotas ({actividades.length} {actividades.length === 1 ? "actividad" : "actividades"})</dt>
        <dd>{formatear(subtotal)}</dd>
        <dt>Bonificación</dt>
        <dd>{porcentaje > 0 ? `− ${porcentaje} %` : "Sin bonificación"}</dd>
        <dt className="resumen__total">Total por mes</dt>
        <dd className="resumen__total">{formatear(total)}</dd>
      </dl>
      {porcentaje === 0 && actividades.length === 1 && (
        <p className="resumen__ayuda">Con 2 actividades tenés 10 % de descuento, y con 3, el 15 %.</p>
      )}
    </section>
  );
}

ResumenCuota.propTypes = {
  actividades: PropTypes.arrayOf(
    PropTypes.shape({
      nombre: PropTypes.string.isRequired,
      cuotaMensual: PropTypes.oneOfType([PropTypes.number, PropTypes.string]).isRequired,
    })
  ).isRequired,
};

export default ResumenCuota;
