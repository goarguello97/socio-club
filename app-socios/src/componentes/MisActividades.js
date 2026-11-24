import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useSocio } from "../contexto/SocioContext";
import { darDeBaja, listarInscripciones, mensajeDeError } from "../servicios/api";
import ResumenCuota from "./ResumenCuota";

/** Ruta "/mis-actividades": inscripciones activas, baja y cuota estimada. */
function MisActividades() {
  const { socio } = useSocio();
  const [inscripciones, setInscripciones] = useState([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState("");
  const [mensaje, setMensaje] = useState("");

  useEffect(() => {
    listarInscripciones(socio.id)
      .then(setInscripciones)
      .catch((e) => setError(mensajeDeError(e)))
      .finally(() => setCargando(false));
  }, [socio.id]);

  async function bajarme(inscripcion) {
    if (!window.confirm(`¿Querés darte de baja de ${inscripcion.actividad.nombre}?`)) {
      return;
    }
    try {
      await darDeBaja(inscripcion.id);
      // Se saca de la lista: React vuelve a dibujar el componente porque cambió el estado
      setInscripciones((actuales) => actuales.filter((i) => i.id !== inscripcion.id));
      setMensaje(`Te diste de baja de ${inscripcion.actividad.nombre}.`);
      setError("");
    } catch (e) {
      setError(mensajeDeError(e));
    }
  }

  return (
    <section aria-labelledby="titulo-mis">
      <div className="cartelera">
        <h1 id="titulo-mis" className="letras">
          Mis actividades
        </h1>
        {socio.estadoCuota === "MOROSO" && (
          <p className="aviso-moroso">
            Tu cuota figura impaga: podés ver y darte de baja, pero para anotarte en algo nuevo tenés que
            regularizarla en secretaría.
          </p>
        )}
      </div>

      {error && <p className="alerta alerta--error">{error}</p>}
      {mensaje && <p className="alerta alerta--exito">{mensaje}</p>}
      {cargando && <p className="estado-carga">Cargando…</p>}

      {!cargando && inscripciones.length === 0 && (
        <div className="vacio">
          <p>Todavía no estás anotado en ninguna actividad.</p>
          <Link className="boton" to="/actividades">
            Ver actividades
          </Link>
        </div>
      )}

      {inscripciones.length > 0 && (
        <>
          <ul className="mis-actividades">
            {inscripciones.map((inscripcion) => (
              <li key={inscripcion.id} className="mis-actividades__item">
                <div>
                  <h2 className="letras">{inscripcion.actividad.nombre}</h2>
                  <p>
                    {inscripcion.actividad.dias} · {inscripcion.actividad.horario} · Profe {inscripcion.actividad.profesor}
                  </p>
                  <p className="mis-actividades__cuota">
                    $ {Number(inscripcion.actividad.cuotaMensual).toLocaleString("es-AR")} por mes
                  </p>
                </div>
                <button type="button" className="boton boton--peligro" onClick={() => bajarme(inscripcion)}>
                  Darme de baja
                </button>
              </li>
            ))}
          </ul>
          <ResumenCuota actividades={inscripciones.map((i) => i.actividad)} />
        </>
      )}
    </section>
  );
}

export default MisActividades;
