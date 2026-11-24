import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSocio } from "../contexto/SocioContext";
import { listarActividades, mensajeDeError } from "../servicios/api";
import TarjetaActividad from "./TarjetaActividad";

/** Ruta "/actividades": catálogo con filtro por disciplina y cupo disponible. */
function ListaActividades() {
  const { socio } = useSocio();
  const navigate = useNavigate();
  const [actividades, setActividades] = useState([]);
  const [disciplinas, setDisciplinas] = useState([]);
  const [disciplina, setDisciplina] = useState("");
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState("");

  // Se ejecuta una sola vez (arreglo vacío): arma la lista de disciplinas para el filtro
  useEffect(() => {
    listarActividades()
      .then((todas) => setDisciplinas([...new Set(todas.map((a) => a.disciplina))].sort()))
      .catch(() => setDisciplinas([]));
  }, []);

  // Se ejecuta cada vez que cambia la disciplina elegida: vuelve a pedir el catálogo filtrado
  useEffect(() => {
    let vigente = true;
    setCargando(true);
    listarActividades(disciplina)
      .then((datos) => {
        if (vigente) {
          setActividades(datos);
          setError("");
        }
      })
      .catch((e) => vigente && setError(mensajeDeError(e)))
      .finally(() => vigente && setCargando(false));
    // Si el socio cambia de filtro antes de que llegue la respuesta, se descarta la anterior
    return () => {
      vigente = false;
    };
  }, [disciplina]);

  return (
    <section aria-labelledby="titulo-actividades">
      <div className="cartelera">
        <h1 id="titulo-actividades" className="letras">
          Actividades
        </h1>
        <div className="filtro">
          <label htmlFor="disciplina" className="letras">
            Disciplina
          </label>
          <select id="disciplina" value={disciplina} onChange={(e) => setDisciplina(e.target.value)}>
            <option value="">Todas</option>
            {disciplinas.map((d) => (
              <option key={d} value={d}>
                {d}
              </option>
            ))}
          </select>
        </div>
      </div>

      {error && <p className="alerta alerta--error">{error}</p>}
      {cargando && !error && <p className="estado-carga">Cargando actividades…</p>}

      {!cargando && !error && actividades.length === 0 && (
        <p className="estado-carga">No hay actividades para esa disciplina.</p>
      )}

      <div className="grilla">
        {actividades.map((actividad) => (
          <TarjetaActividad
            key={actividad.id}
            actividad={actividad}
            esParaMiEdad={socio.edad >= actividad.edadMinima && socio.edad <= actividad.edadMaxima}
            onElegir={(id) => navigate(`/actividades/${id}`)}
          />
        ))}
      </div>
    </section>
  );
}

export default ListaActividades;
