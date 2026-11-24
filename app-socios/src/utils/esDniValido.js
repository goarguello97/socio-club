/**
 * RN7: el DNI tiene 7 u 8 dígitos numéricos, sin puntos ni espacios.
 * Es la misma regla que usa el sitio público.
 *
 * @param {string} dni
 * @returns {boolean}
 */
export function esDniValido(dni) {
  if (typeof dni !== "string") {
    return false;
  }
  return /^\d{7,8}$/.test(dni);
}

export default esDniValido;
