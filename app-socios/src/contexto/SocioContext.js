import { createContext, useContext, useState } from "react";
import PropTypes from "prop-types";

/**
 * El socio que ingresó se comparte con un contexto: cualquier componente lo lee con useSocio()
 * sin tener que pasarlo por props de componente en componente.
 */
const SocioContext = createContext(null);

const CLAVE = "socioclub.socio";

function leerSocioGuardado() {
  // sessionStorage: si se recarga la página el socio sigue adentro; al cerrar la pestaña, sale
  try {
    const guardado = sessionStorage.getItem(CLAVE);
    return guardado ? JSON.parse(guardado) : null;
  } catch {
    return null;
  }
}

export function SocioProvider({ children }) {
  const [socio, setSocio] = useState(leerSocioGuardado);

  function ingresar(datosSocio) {
    setSocio(datosSocio);
    try {
      sessionStorage.setItem(CLAVE, JSON.stringify(datosSocio));
    } catch {
      // Si el navegador no deja guardar, el socio igual queda en el estado mientras dure la sesión
    }
  }

  function salir() {
    setSocio(null);
    try {
      sessionStorage.removeItem(CLAVE);
    } catch {
      // nada que limpiar
    }
  }

  return <SocioContext.Provider value={{ socio, ingresar, salir }}>{children}</SocioContext.Provider>;
}

SocioProvider.propTypes = {
  children: PropTypes.node.isRequired,
};

export function useSocio() {
  return useContext(SocioContext);
}
