# App "Mi Club"

App React para que cada socio del Club Los Algarrobos ingrese con su DNI, vea el catálogo con el cupo disponible, se inscriba, se dé de baja y consulte su cuota mensual estimada.

Creada con create-react-app. Usa React Router, Axios y Context. Necesita el backend levantado en `http://localhost:8080`; ver el [README principal](../README.md).

## Comandos

| Comando | Qué hace |
|---|---|
| `npm install` | Instala las dependencias (la primera vez) |
| `npm start` | Levanta la app en http://localhost:3000 |
| `npm test` | Corre las pruebas de Jest y React Testing Library |
| `npm run build` | Genera la versión de producción en `build/` |

Para apuntar a otro backend: `REACT_APP_API_URL=http://otro-servidor:8080/api npm start`.

## Rutas

| Ruta | Componente | Qué muestra |
|---|---|---|
| `/` | `Ingreso` | Ingreso con DNI (valida RN7 antes de consultar la API) |
| `/actividades` | `ListaActividades` | Catálogo con filtro por disciplina y cupo disponible |
| `/actividades/:id` | `DetalleActividad` | Detalle con el botón "Inscribirme" y los mensajes 409 del backend |
| `/mis-actividades` | `MisActividades` + `ResumenCuota` | Inscripciones activas, baja y cuota estimada (RN6) |

## Estructura

```
src/
├── App.js                  BrowserRouter, rutas y SocioProvider
├── contexto/SocioContext.js  Socio que ingresó, compartido con createContext/useContext
├── servicios/api.js        Toda la comunicación con la API (Axios)
├── utils/                  calcularCuotaMensual (RN6) y esDniValido (RN7), con pruebas
└── componentes/            Encabezado, Pie, Ingreso, ListaActividades, TarjetaActividad,
                            DetalleActividad, MisActividades, ResumenCuota, RutaPrivada
```
