import { NavLink, useNavigate } from "react-router-dom";
import { useSocio } from "../contexto/SocioContext";

function Encabezado() {
  const { socio, salir } = useSocio();
  const navigate = useNavigate();

  function cerrarSesion() {
    salir();
    navigate("/");
  }

  return (
    <header className="encabezado">
      <div className="encabezado__marca">
        <img src="/escudo.svg" alt="Escudo del club Los Algarrobos" width="40" height="47" />
        <span>
          Mi Club
          <small>Los Algarrobos</small>
        </span>
      </div>

      {socio && (
        <>
          <nav className="encabezado__nav" aria-label="Principal">
            <NavLink to="/actividades">Actividades</NavLink>
            <NavLink to="/mis-actividades">Mis actividades</NavLink>
          </nav>
          <div className="encabezado__socio">
            <span>Hola, {socio.nombre}</span>
            <button type="button" className="boton boton--chico boton--secundario" onClick={cerrarSesion}>
              Salir
            </button>
          </div>
        </>
      )}
    </header>
  );
}

export default Encabezado;
