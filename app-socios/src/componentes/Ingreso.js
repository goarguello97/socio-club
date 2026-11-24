import { useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import { useSocio } from "../contexto/SocioContext";
import { buscarSocioPorDni, mensajeDeError } from "../servicios/api";
import { esDniValido } from "../utils/esDniValido";

/** Ruta "/": el socio escribe su DNI y la app lo busca en la API (RF8). */
function Ingreso() {
  const { socio, ingresar } = useSocio();
  const navigate = useNavigate();
  const [dni, setDni] = useState("");
  const [error, setError] = useState("");
  const [buscando, setBuscando] = useState(false);

  // Si ya ingresó, no tiene sentido mostrarle el formulario
  if (socio) {
    return <Navigate to="/actividades" replace />;
  }

  async function enviar(evento) {
    evento.preventDefault();
    const dniLimpio = dni.trim();
    if (!esDniValido(dniLimpio)) {
      setError("El DNI va sin puntos: 7 u 8 números, por ejemplo 35123456.");
      return;
    }
    setError("");
    setBuscando(true);
    try {
      const encontrado = await buscarSocioPorDni(dniLimpio);
      ingresar(encontrado);
      navigate("/actividades");
    } catch (e) {
      setError(
        e.response && e.response.status === 404
          ? "No encontramos ningún socio con ese DNI. Si no sos socio, acercate a la secretaría."
          : mensajeDeError(e)
      );
    } finally {
      setBuscando(false);
    }
  }

  return (
    <section className="ingreso cartelera" aria-labelledby="titulo-ingreso">
      <h1 id="titulo-ingreso" className="letras">
        Bienvenido a Mi Club
      </h1>
      <p className="bajada letras">Entrá con tu DNI</p>

      <form onSubmit={enviar} noValidate>
        <label htmlFor="dni" className="letras">
          DNI
        </label>
        <input
          id="dni"
          name="dni"
          inputMode="numeric"
          autoComplete="off"
          placeholder="35123456"
          value={dni}
          onChange={(e) => setDni(e.target.value)}
          aria-invalid={error !== ""}
          aria-describedby="error-dni"
        />
        <p id="error-dni" className="mensaje-error" role="alert">
          {error}
        </p>
        <button type="submit" className="boton" disabled={buscando}>
          {buscando ? "Buscando…" : "Ingresar"}
        </button>
      </form>
    </section>
  );
}

export default Ingreso;
