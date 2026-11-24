import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { useSocio } from "../contexto/SocioContext";
import { inscribir, listarInscripciones, mensajeDeError, obtenerActividad } from "../servicios/api";

/** Ruta "/actividades/:id": detalle de la actividad con el botón "Inscribirme". */
function DetalleActividad() {
  const { id } = useParams();
  const { socio } = useSocio();
  const [actividad, setActividad] = useState(null);
  const [yaInscripto, setYaInscripto] = useState(false);
  const [cargando, setCargando] = useState(true);
  const [enviando, setEnviando] = useState(false);
  const [error, setError] = useState("");
  const [exito, setExito] = useState("");

  useEffect(() => {
    setCargando(true);
    Promise.all([obtenerActividad(id), listarInscripciones(socio.id)])
      .then(([datos, inscripciones]) => {
        setActividad(datos);
        setYaInscripto(inscripciones.some((i) => i.actividad.id === datos.id));
      })
      .catch((e) => setError(mensajeDeError(e)))
      .finally(() => setCargando(false));
  }, [id, socio.id]);

  async function inscribirme() {
    setEnviando(true);
    setError("");
    try {
      const inscripcion = await inscribir(socio.id, actividad.id);
      setActividad(inscripcion.actividad); // trae el cupo disponible actualizado
      setYaInscripto(true);
      setExito(`¡Listo! Quedaste inscripto en ${inscripcion.actividad.nombre}.`);
    } catch (e) {
      // 409: el backend explica qué regla no se cumple (cupo, edad, cuota, máximo...)
      setError(mensajeDeError(e));
    } finally {
      setEnviando(false);
    }
  }

  if (cargando) {
    return <p className="estado-carga">Cargando actividad…</p>;
  }

  if (!actividad) {
    return (
      <>
        <p className="alerta alerta--error">{error || "No encontramos esa actividad."}</p>
        <Link to="/actividades">← Volver a actividades</Link>
      </>
    );
  }

  const sinCupo = actividad.cupoDisponible === 0;

  return (
    <article className="detalle cartelera" aria-labelledby="titulo-detalle">
      <h1 id="titulo-detalle" className="letras">
        {actividad.nombre}
      </h1>
      <p className="bajada letras">{actividad.disciplina}</p>

      <dl className="detalle__datos">
        <dt>Días y horario</dt>
        <dd>
          {actividad.dias} · {actividad.horario}
        </dd>
        <dt>Edades</dt>
        <dd>
          {actividad.edadMinima} a {actividad.edadMaxima} años
        </dd>
        <dt>Profe</dt>
        <dd>{actividad.profesor}</dd>
        <dt>Cuota mensual</dt>
        <dd>$ {Number(actividad.cuotaMensual).toLocaleString("es-AR")}</dd>
        <dt>Lugares libres</dt>
        <dd className={sinCupo ? "texto-sin-cupo" : ""}>
          {sinCupo ? "Sin cupo" : `${actividad.cupoDisponible} de ${actividad.cupoMaximo}`}
        </dd>
      </dl>

      {error && <p className="alerta alerta--error" role="alert">{error}</p>}
      {exito && <p className="alerta alerta--exito" role="status">{exito}</p>}

      <div className="detalle__acciones">
        {yaInscripto ? (
          <Link className="boton" to="/mis-actividades">
            Ver mis actividades
          </Link>
        ) : (
          <button type="button" className="boton" onClick={inscribirme} disabled={sinCupo || enviando}>
            {enviando ? "Inscribiendo…" : "Inscribirme"}
          </button>
        )}
        <Link className="boton boton--secundario" to="/actividades">
          Volver
        </Link>
      </div>
      {yaInscripto && !exito && <p className="detalle__nota">Ya estás inscripto en esta actividad.</p>}
    </article>
  );
}

export default DetalleActividad;
