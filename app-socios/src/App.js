import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { SocioProvider } from "./contexto/SocioContext";
import Encabezado from "./componentes/Encabezado";
import Pie from "./componentes/Pie";
import Ingreso from "./componentes/Ingreso";
import ListaActividades from "./componentes/ListaActividades";
import DetalleActividad from "./componentes/DetalleActividad";
import MisActividades from "./componentes/MisActividades";
import RutaPrivada from "./componentes/RutaPrivada";
import "./App.css";

function App() {
  return (
    <SocioProvider>
      <BrowserRouter>
        <Encabezado />
        <main className="contenido">
          <Routes>
            <Route path="/" element={<Ingreso />} />
            <Route path="/actividades" element={<RutaPrivada><ListaActividades /></RutaPrivada>} />
            <Route path="/actividades/:id" element={<RutaPrivada><DetalleActividad /></RutaPrivada>} />
            <Route path="/mis-actividades" element={<RutaPrivada><MisActividades /></RutaPrivada>} />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </main>
        <Pie />
      </BrowserRouter>
    </SocioProvider>
  );
}

export default App;
