# Plan de trabajo — TP Integrador SocioClub

Club Social y Deportivo Los Algarrobos · Taller de Construcción de Software · Universidad Siglo 21

El grupo expone en la **Semana 1** (hito de ~40 %). Lo que sigue después de la exposición se evalúa recién en la **entrega final**, pero conviene respetar el orden de los hitos de la consigna (sección 6) porque cada parte se apoya en la anterior.

**Cómo usar este plan:**
- Completen la columna **Responsable** cuando se repartan las tareas.
- Actualicen el **Estado** con un commit cada vez que cierren una tarea.
- Estados posibles: ✅ hecho · 🔄 en curso · ⬜ pendiente.

---

## 1. Hitos

| Hito | Qué tiene que estar funcionando | Estado | Fecha |
|---|---|---|---|
| **Semana 1** (exposición del grupo) | Parte A completa. Parte B: proyecto en capas, entidades, repositorios, MySQL con datos iniciales y ABM de actividades en Thymeleaf. Este plan de trabajo. | ✅ | _completar_ |
| **Semana 2** (~70 %) | Parte B completa: socios con paginación y búsqueda; inscripciones con RN1 a RN5. Parte D iniciada con datos locales: rutas, componentes, props, estado y contexto. | ⬜ | _completar_ |
| **Semana 3** (~90 %) | Parte C completa y probada en Postman. Parte D conectada con Axios: ingreso por DNI, inscripción y baja reales. Parte E iniciada (Jest). | ⬜ | _completar_ |
| **Entrega final** (100 %) | Partes A a F completas, con las devoluciones de la exposición incorporadas. PDF `TPI_SocioClub_GrupoN.pdf` con el enlace al repositorio en la primera página. | ⬜ | última clase |

---

## 2. Tareas por parte

### Parte A — Sitio público (`/sitio-publico`)

| ID | Tarea | Hito | Responsable | Estado |
|---|---|---|---|---|
| A1 | Tres páginas con HTML5 semántico: `index.html`, `actividades.html` y `preinscripcion.html` | S1 | | ✅ |
| A2 | `estilos.css` externo con selectores de tipo, clase, ID y atributo, y una media query (tarjetas en una columna en celular) | S1 | | ✅ |
| A3 | Clase `Actividad` con `esAptaParaEdad(edad)` y catálogo generado desde un arreglo | S1 | | ✅ |
| A4 | Filtro por edad y disciplina con `addEventListener` y manipulación del DOM | S1 | | ✅ |
| A5 | Preinscripción validada (obligatorios, email, DNI según RN7) con errores junto al campo, sin `alert()` | S1 | | ✅ |
| A6 | Objetos del navegador: `location` (lee `?edad=`), `history` (`replaceState` y botón "Volver") | S1 | | ✅ |
| A7 | Reemplazar los **textos de ejemplo** (historia y dirección del club, marcados con comentarios en el HTML) | Final | | ⬜ |
| A8 | _Decisión abierta:_ ¿la preinscripción se envía al backend? Ver sección 3 | Final | | ⬜ |

### Parte B — Sistema de secretaría (`/backend`, Thymeleaf)

| ID | Tarea | Hito | Responsable | Estado |
|---|---|---|---|---|
| B1 | Proyecto Maven (Spring Boot 3.5, Java 17) con paquetes `model`, `repository`, `service` y `controller` | S1 | | ✅ |
| B2 | Entidades `Socio`, `Actividad` e `Inscripcion` con `@ManyToOne` / `@OneToMany` | S1 | | ✅ |
| B3 | Repositorios con métodos derivados y `@Query` (`contarActivasPorActividad`) | S1 | | ✅ |
| B4 | MySQL en Docker con `database/socioclub.sql`: 8 actividades, 20 socios (2 morosos) y Yoga con 14/15 | S1 | | ✅ |
| B5 | ABM de actividades con cupo disponible, fragmentos de encabezado y pie, y mensajes de error | S1 | | ✅ |
| B6 | `SocioService` y listado paginado de socios (`Pageable`) con búsqueda por apellido | S2 | | ⬜ |
| B7 | Alta y edición de socios (DNI único, validación de DNI, estado de cuota) | S2 | | ⬜ |
| B8 | `InscripcionService` con **RN1 a RN5** (cupo, duplicado, edad, moroso, máximo 3) | S2 | | ⬜ |
| B9 | Pantalla de inscripción (elegir socio y actividad) mostrando el mensaje de la regla que falla | S2 | | ⬜ |
| B10 | Listado de inscriptos activos de una actividad (RF7) con baja lógica desde secretaría (RF9) | S2 | | ⬜ |
| B11 | Volver a mostrar en el menú los enlaces a Socios e Inscribir (hoy están comentados en `layout.html`) | S2 | | ⬜ |
| B12 | _Opcional:_ alinear la estética del backoffice con `DESIGN.md` | Final | | ⬜ |

### Parte C — API REST (`/backend`, bajo `/api`)

| ID | Tarea | Hito | Responsable | Estado |
|---|---|---|---|---|
| C1 | `ActividadRestController`: GET (con `?disciplina=`), GET por id, POST, PUT y DELETE (409 si tiene inscriptos) | S3 | | ⬜ |
| C2 | `SocioRestController`: `GET /api/socios/dni/{dni}` y `GET /api/socios/{id}/inscripciones` | S3 | | ⬜ |
| C3 | `InscripcionRestController`: `POST /api/inscripciones` y `PUT /api/inscripciones/{id}/baja` | S3 | | ⬜ |
| C4 | Manejo de errores: 404 para `RecursoNoEncontradoException` y 409 con `{ "error", "regla" }` para `ReglaNegocioException` (las excepciones ya existen) | S3 | | ⬜ |
| C5 | Resolver el **ciclo JSON** Socio ↔ Inscripcion (ver sección 3) y poder explicarlo en la defensa | S3 | | ⬜ |
| C6 | CORS para `http://localhost:3000` | S3 | | ⬜ |
| C7 | Colección de Postman con todos los endpoints y los casos de error de RN1 a RN5; exportarla en `.json` | S3 | | ⬜ |

### Parte D — App "Mi Club" (`/app-socios`, React)

| ID | Tarea | Hito | Responsable | Estado |
|---|---|---|---|---|
| D1 | Proyecto con create-react-app y React Router: `/`, `/actividades`, `/actividades/:id`, `/mis-actividades` | S2 | | ⬜ |
| D2 | Componentes `Encabezado`, `Pie`, `TarjetaActividad`, `ListaActividades`, `DetalleActividad`, `MisActividades` y `ResumenCuota` con datos locales | S2 | | ⬜ |
| D3 | Contexto del socio con `createContext` / `useContext` | S2 | | ⬜ |
| D4 | `PropTypes` en `TarjetaActividad` y `ResumenCuota`; renderizado condicional ("Sin cupo", lista vacía) y clase dinámica | S2 | | ⬜ |
| D5 | `src/servicios/api.js` con Axios (toda la comunicación centralizada) | S3 | | ⬜ |
| D6 | Ingreso por DNI, inscripción y baja reales contra la API, mostrando los mensajes 409 | S3 | | ⬜ |
| D7 | Cuota mensual estimada (RN6) en `ResumenCuota` usando `calcularCuotaMensual` | S3 | | ⬜ |
| D8 | Estilos en CSS externo siguiendo `DESIGN.md` (misma identidad que el sitio, más sobria) | Final | | ⬜ |

### Parte E — Pruebas

| ID | Tarea | Hito | Responsable | Estado |
|---|---|---|---|---|
| E1 | Funciones puras en `src/utils`: `calcularCuotaMensual(actividades)` y `esDniValido(dni)` | S3 | | ⬜ |
| E2 | 6 o más pruebas Jest: 0, 1, 2 y 3 actividades; DNI con puntos, con letras, con 6 y con 9 dígitos | S3 | | ⬜ |
| E3 | 2 o más pruebas con React Testing Library sobre `TarjetaActividad` (muestra el nombre; cupo 0 → "Sin cupo" y botón deshabilitado) | Final | | ⬜ |
| E4 | Prueba de sistema: tabla de 6 casos de punta a punta (1 exitoso + RN1 a RN5) con resultado esperado y obtenido | Final | | ⬜ |

### Parte F — Documentación y bitácora

| ID | Tarea | Hito | Responsable | Estado |
|---|---|---|---|---|
| F1 | Subir el repositorio a GitHub (público o con el docente invitado) | **Ya** | | ⬜ |
| F2 | Commits de **todos** los integrantes a lo largo del cuatrimestre | Continuo | todos | ⬜ |
| F3 | `README.md` en la raíz: integrantes, cómo levantar la base, el backend, el sitio y la app, y DNI de prueba | Final | | ⬜ |
| F4 | Informe técnico en PDF (8 a 12 páginas) con arquitectura, modelo de datos, endpoints, capturas, pruebas y conclusiones | Final | | ⬜ |
| F5 | Bitácora de uso de IA: 5 o más entradas, al menos 3 con un error detectado y corregido (ver sección 5) | Final | | 🔄 |

---

## 3. Decisiones abiertas

| Tema | Opciones | Recomendación |
|---|---|---|
| Ciclo JSON Socio ↔ Inscripcion (C5) | DTOs propios para la API, o `@JsonIgnore` / `@JsonManagedReference` en las entidades | **DTOs**: separan la API del modelo, evitan el ciclo de raíz y son fáciles de explicar en la defensa |
| Preinscripción del sitio público (A8) | Solo se valida en el navegador, o se agrega un endpoint `POST /api/preinscripciones` con su tabla | Decidirlo antes de la Semana 3. Si se agrega, es un extra que hay que poder defender |
| Repositorio | Público o privado con el docente invitado | Crearlo ya (F1), así cada integrante empieza a commitear |

---

## 4. Riesgos

| Riesgo | Mitigación |
|---|---|
| create-react-app está deprecado y puede fallar con Node 24 (el de esta PC) | Probar D1 cuanto antes. Si falla, instalar Node 20 LTS (por ejemplo con nvm-windows). La consigna exige create-react-app, no Vite |
| El puerto 3306 ya lo usa un MySQL local | Docker publica MySQL en el **3307**; ya está configurado en `application.properties` |
| En la defensa preguntan a cualquiera sobre cualquier parte | Después de cada tarea, quien la hizo la explica a otro integrante. Repasar en grupo el recorrido clic → controlador → servicio → repositorio → MySQL |
| El docente pide un cambio en vivo (por ejemplo, máximo de actividades de 3 a 4) | Las reglas viven en un solo lugar: `InscripcionService` (RN5) y `calcularCuotaMensual` (RN6). Ensayar el cambio antes |
| Lo que queda para la entrega se acumula al final | Respetar el orden de los hitos aunque ya hayan expuesto, y revisar este plan una vez por semana |

---

## 5. Material ya disponible para la bitácora de IA

Correcciones reales que surgieron durante la Semana 1 y que sirven como entradas con "qué estaba mal y cómo lo corregimos":

1. **Versión de Spring Boot (B1):** Spring Initializr ya solo ofrece Spring Boot 4. Se usó 3.5.16 (última 3.x) para mantener `spring-boot-starter-web` y Jackson 2, como en las lecturas.
2. **Thymeleaf (B5):** una expresión con el nombre de la actividad dentro de `onsubmit` fallaba, porque Thymeleaf bloquea variables en atributos de eventos por seguridad. Se dejó un mensaje fijo.
3. **`;jsessionid` en la URL (B5):** al redirigir con mensajes flash, Spring agregaba la sesión a la URL. Se configuró `server.servlet.session.tracking-modes=cookie`.
4. **Fichas del catálogo (A3):** la primera versión eran tarjetas genéricas idénticas. La revisión pidió escribirlas en letras sobre el paño de la cartelera, como el resto del sitio.
5. **Diseño responsivo (A2):** en celular las fichas se salían del borde de la cartelera porque la columna `1fr` no achicaba por debajo del contenido. Se corrigió con `minmax(0, 1fr)`.
6. **Área táctil (A2):** el botón "Preinscribirme" quedó de 26 px de alto en celular. Se amplió el área tocable a 44 px sin cambiar el dibujo.

---

## 6. Reparto sugerido (para 4 o 5 integrantes)

Es solo una propuesta. Cada uno tiene que poder explicar todo el sistema, no solo su parte.

| Integrante | Foco principal |
|---|---|
| 1 | Parte B: socios e inscripciones con RN1 a RN5 (B6–B11) |
| 2 | Parte C: API REST, errores, DTOs, CORS y Postman (C1–C7) |
| 3 | Parte D: estructura, componentes y contexto (D1–D4) |
| 4 | Parte D conectada y Parte E: Axios, RN6, Jest y RTL (D5–D7, E1–E3) |
| 5 (si hay) | Partes A y F: textos reales del sitio, README, informe, prueba de sistema y bitácora (A7, E4, F3–F5) |
