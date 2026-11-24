import PropTypes from "prop-types";
import { Navigate } from "react-router-dom";
import { useSocio } from "../contexto/SocioContext";

/** Si nadie ingresó con su DNI, manda al ingreso en lugar de mostrar la pantalla. */
function RutaPrivada({ children }) {
  const { socio } = useSocio();
  if (!socio) {
    return <Navigate to="/" replace />;
  }
  return children;
}

RutaPrivada.propTypes = {
  children: PropTypes.node.isRequired,
};

export default RutaPrivada;
