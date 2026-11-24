/**
 * RN6: porcentaje de bonificación según la cantidad de actividades activas.
 * 2 actividades -> 10 % · 3 actividades -> 15 % · si no, sin descuento.
 */
export function porcentajeDescuento(cantidadActividades) {
  if (cantidadActividades >= 3) {
    return 15;
  }
  if (cantidadActividades === 2) {
    return 10;
  }
  return 0;
}

/**
 * RN6: cuota mensual estimada = suma de las cuotas de las actividades activas,
 * con el descuento que corresponda. Es una función pura: no usa estado ni hace pedidos.
 *
 * @param {Array<{cuotaMensual: number}>} actividades
 * @returns {number} total a pagar por mes, en pesos
 */
export function calcularCuotaMensual(actividades) {
  if (!Array.isArray(actividades) || actividades.length === 0) {
    return 0;
  }
  const subtotal = actividades.reduce((suma, actividad) => suma + Number(actividad.cuotaMensual), 0);
  const descuento = (subtotal * porcentajeDescuento(actividades.length)) / 100;
  return Math.round(subtotal - descuento);
}

export default calcularCuotaMensual;
