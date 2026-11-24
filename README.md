# SocioClub — Club Social y Deportivo Los Algarrobos

Sistema web de gestión de socios, actividades e inscripciones para un club de barrio de Córdoba.
Trabajo Práctico Integrador de **Taller de Construcción de Software** (Universidad Siglo 21).

---

## Cómo ejecutar el proyecto

### Requisitos

| Herramienta | Versión probada | Para qué |
|---|---|---|
| [Docker Desktop](https://www.docker.com/products/docker-desktop/) | 26 | Base de datos MySQL 8 |
| [Java JDK](https://adoptium.net/) | 17 | Backend Spring Boot (Maven viene incluido con `mvnw`) |
| [Node.js](https://nodejs.org/) | 24 (con npm 11) | App React de socios |
| [Python](https://www.python.org/) | 3.12 | Servidor local del sitio público (o la extensión Live Server de VS Code) |

Cada parte se levanta en su propia terminal, desde la raíz del repositorio y en este orden.

### 1. Base de datos

```bash
docker compose up -d --wait
```

Levanta MySQL 8 en el puerto **3307** (para no chocar con un MySQL local en el 3306) y espera a que esté lista. La primera vez crea las tablas y carga los datos iniciales desde [database/socioclub.sql](database/socioclub.sql).

Para **volver a los datos iniciales** (borra todo lo que se haya cargado), y después levantar de nuevo el backend:

```bash
docker compose down -v
```

```bash
docker compose up -d --wait
```

### 2. Backend: sistema de secretaría y API REST

En Windows (PowerShell):

```powershell
cd backend; .\mvnw spring-boot:run
```

En macOS o Linux:

```bash
cd backend && ./mvnw spring-boot:run
```

Cuando aparece `Started SocioclubApplication`, el sistema de secretaría queda en **http://localhost:8080** y la API en **http://localhost:8080/api**.

> Si falla con `Communications link failure`, la base no estaba lista: ejecutá `docker compose up -d --wait` y volvé a levantar el backend.

### 3. Sitio público

Hay que servirlo con un servidor local: si se abre con doble clic (`file://`) no cargan los estilos ni los scripts.

```bash
python -m http.server 5500 --directory sitio-publico
```

El sitio queda en **http://localhost:5500**.

### 4. App "Mi Club" para socios

La primera vez hay que instalar las dependencias:

```bash
cd app-socios && npm install
```

Después, para levantarla:

```bash
cd app-socios && npm start
```

La app queda en **http://localhost:3000** (necesita el backend levantado). Se ingresa con un DNI de la tabla de datos de prueba, por ejemplo `45901234`.

### Direcciones y puertos

| Servicio | URL / puerto |
|---|---|
| Sitio público | http://localhost:5500 |
| Sistema de secretaría | http://localhost:8080 |
| API REST | http://localhost:8080/api |
| App de socios | http://localhost:3000 |
| MySQL | `localhost:3307` · base `socioclub` · usuario `socioclub` · contraseña `socioclub123` |

---

## Cómo correr las pruebas

| Pruebas | Comando | Qué cubren |
|---|---|---|
| Jest y React Testing Library (18) | `cd app-socios && npm test -- --watchAll=false` | `calcularCuotaMensual` (RN6), `esDniValido` (RN7) y el componente `TarjetaActividad` |
| JUnit con Mockito (8) | `cd backend && ./mvnw test` | Las reglas RN1 a RN5 y la baja lógica en `InscripcionService` |
| Postman (25 pedidos, 44 verificaciones) | Importar [postman/SocioClub.postman_collection.json](postman/SocioClub.postman_collection.json) y correr la colección | Todos los endpoints, con los casos exitosos y de error (404, 409 de RN1 a RN5, 400) |

La colección de Postman se corre **con la base recién cargada** (`docker compose down -v` y `up -d --wait`, y el backend reiniciado), porque los pedidos van en orden y algunos usan datos que crearon los anteriores. También se puede correr desde la terminal:

```bash
npx newman run postman/SocioClub.postman_collection.json
```

---

## Datos de prueba

La base arranca con las 8 actividades del enunciado y **20 socios de prueba**, pensados para demostrar cada regla de negocio. Todos los datos son ficticios.

| DNI | Socio | Edad aprox. | Cuota | Para demostrar |
|---|---|---|---|---|
| `45901234` | Nicolás Vega | 22 | Al día | **Caso exitoso:** sin actividades, toma el último lugar de Yoga |
| `51678901` | Camila Ortiz | 14 | Al día | **RN1:** después de Vega, Yoga queda sin cupo |
| `35123456` | Juan Pereyra | 36 | Al día | **RN2:** ya está en Vóley mixto |
| `52345678` | Mateo Castro | 14 | Al día | **RN3:** no entra en Fútbol infantil (6 a 12) |
| `38901234` | Marcos López | 31 | **Moroso** | **RN4:** no puede inscribirse a nada nuevo |
| `30456123` | Gabriel Torres | 43 | **Moroso** | **RN4** |
| `31456789` | Lucía Gómez | 41 | Al día | **RN5:** ya tiene 3 actividades; cuota con 15 % de descuento |
| `54789012` | Tomás Herrera | 12 | Al día | Un chico con 2 actividades; cuota con 10 % de descuento |

- **RN1 (cupo):** Yoga tiene **14 de 15** lugares ocupados. La primera inscripción nueva entra y la siguiente se rechaza.
- **RN7 (DNI):** `35.123.456` (con puntos), `ABC12345`, `123456` (6 dígitos) y `123456789` (9 dígitos) se rechazan en el sitio, en la app y en el alta de socios.

El resto de los socios está en [database/socioclub.sql](database/socioclub.sql).

---

## Qué es

El club tiene alrededor de 650 socios y 8 actividades. Hoy la gestión es manual: cupos excedidos, inscripciones duplicadas, edades fuera de rango y socios morosos que se siguen anotando. El sistema tiene **tres caras sobre una sola base de datos y un solo backend**:

| Componente | Para quién | Tecnología | Carpeta |
|---|---|---|---|
| A. Sitio público | Vecinos | HTML5, CSS3, JavaScript | [sitio-publico/](sitio-publico/) |
| B. Sistema de secretaría | Secretaría y Comisión Directiva | Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA, MySQL | [backend/](backend/) |
| C. API REST | App de socios | Spring Boot `@RestController`, JSON | [backend/](backend/) |
| D. App "Mi Club" | Socios | React (create-react-app), React Router, Axios, Context | [app-socios/](app-socios/) |
| E. Pruebas | El equipo | Jest, React Testing Library, JUnit, Postman | — |

### Arquitectura

```
 [ Navegador del vecino ]   [ Navegador de secretaría ]   [ Celular del socio ]
   Sitio público (A)           Vistas Thymeleaf (B)         App React (D)
   HTML + CSS + JS                     |                        | Axios (JSON)
                                       v                        v
              +---------------------------------------------------+
              |      BACKEND SPRING BOOT (un solo proyecto)        |
              |   Controladores MVC (B)    Controladores REST (C)  |
              |   Capa de SERVICIO   (reglas de negocio RN1-RN5)   |
              |   Capa de REPOSITORIO (Spring Data JPA)            |
              |   Capa de MODELO     (entidades JPA)               |
              +--------------------------+------------------------+
                                         v
                              [ MySQL 8 en Docker ]
```

Las reglas de negocio se escriben **una sola vez**, en `InscripcionService`. Una inscripción que entra por el sistema de secretaría o por la app se valida exactamente igual.

### Reglas de negocio

| Regla | Qué controla | Dónde se implementa |
|---|---|---|
| RN1 | Una actividad no puede superar su cupo de inscripciones activas | `InscripcionService` |
| RN2 | Un socio no puede estar dos veces en la misma actividad | `InscripcionService` |
| RN3 | La edad del socio tiene que estar dentro del rango de la actividad | `InscripcionService` |
| RN4 | Un socio moroso no puede inscribirse a actividades nuevas | `InscripcionService` |
| RN5 | Máximo 3 actividades activas por socio (constante `MAXIMO_ACTIVIDADES`) | `InscripcionService` |
| RN6 | Cuota estimada: suma de cuotas, 10 % de descuento con 2 actividades y 15 % con 3 | `app-socios/src/utils/calcularCuotaMensual.js` |
| RN7 | DNI de 7 u 8 dígitos, sin puntos | JavaScript del sitio y de la app (`esDniValido`) y `SocioService` |

### API REST

| Verbo | Ruta | Qué hace | Respuestas |
|---|---|---|---|
| GET | `/api/actividades` | Lista las actividades con su cupo disponible. Acepta `?disciplina=Fútbol` | 200 |
| GET | `/api/actividades/{id}` | Detalle de una actividad | 200 / 404 |
| POST | `/api/actividades` | Crea una actividad | 201 / 400 |
| PUT | `/api/actividades/{id}` | Modifica una actividad | 200 / 404 / 409 |
| DELETE | `/api/actividades/{id}` | Elimina una actividad sin inscriptos activos | 204 / 404 / 409 |
| GET | `/api/socios/dni/{dni}` | Busca un socio por DNI (ingreso a la app) | 200 / 404 |
| GET | `/api/socios/{id}/inscripciones` | Inscripciones activas de un socio | 200 / 404 |
| POST | `/api/inscripciones` | Inscribe un socio: `{ "socioId": 3, "actividadId": 1 }`. Aplica RN1 a RN5 | 201 / 404 / 409 |
| PUT | `/api/inscripciones/{id}/baja` | Baja lógica: el estado pasa a BAJA | 200 / 404 |

Cuando una regla rechaza la operación, la respuesta es `409 Conflict` con el mensaje y la regla:

```json
{ "error": "La actividad no tiene cupo disponible", "regla": "RN1" }
```

---

## Estructura del repositorio

```
├── backend/                       Spring Boot (Maven): secretaría + API REST
│   └── src/
│       ├── main/java/ar/edu/siglo21/socioclub/
│       │   ├── model/             Entidades JPA: Socio, Actividad, Inscripcion y enums
│       │   ├── repository/        Interfaces JpaRepository (métodos derivados y @Query)
│       │   ├── service/           Reglas de negocio y excepciones
│       │   ├── controller/        Controladores MVC (Thymeleaf), REST y manejo de errores
│       │   ├── dto/               Objetos que viajan en el JSON de la API
│       │   └── config/            CORS para la app React
│       ├── main/resources/
│       │   ├── templates/         Vistas Thymeleaf y fragmentos comunes
│       │   └── static/css/        Estilos del sistema de secretaría
│       └── test/                  Pruebas JUnit de InscripcionService
├── sitio-publico/                 index.html, actividades.html, preinscripcion.html, css, js, img
├── app-socios/                    App React "Mi Club"
│   └── src/
│       ├── componentes/           Encabezado, Pie, TarjetaActividad, ListaActividades, ...
│       ├── contexto/              SocioContext (socio que ingresó)
│       ├── servicios/api.js       Toda la comunicación con el backend (Axios)
│       └── utils/                 calcularCuotaMensual y esDniValido, con sus pruebas
├── database/socioclub.sql         Creación de tablas y datos iniciales
├── postman/                       Colección de Postman de la API
├── docs/
│   ├── plan-de-trabajo.md         Hitos, tareas por parte, decisiones y riesgos
│   └── informe/                   Informe técnico (PDF y fuente)
├── docker-compose.yml             MySQL 8
├── PRODUCT.md                     Contexto del producto
└── DESIGN.md                      Sistema de diseño del sitio público
```

---

## Decisiones técnicas

- **Spring Boot 3.5 con Java 17.** Spring Initializr ya ofrece solo la versión 4. Se usó la última 3.x para mantener `spring-boot-starter-web` y Jackson 2, como en las lecturas.
- **Solo tecnologías de la materia.** Sin Lombok, Spring Security, Vite, Tailwind ni Redux. La app usa create-react-app, Axios y Context.
- **DTOs en la API.** La relación de ida y vuelta Socio ↔ Inscripcion ↔ Actividad generaba un ciclo infinito al pasar las entidades a JSON. La API devuelve clases propias (`ActividadDTO`, `SocioDTO`, `InscripcionDTO`) sin la relación de vuelta.
- **Un solo manejo de errores para la API** (`ApiExceptionHandler` con `@RestControllerAdvice`): 404 cuando algo no existe, 409 cuando una regla rechaza la operación y 400 cuando los datos están mal cargados. Las vistas Thymeleaf muestran los mismos mensajes en pantalla.
- **El esquema lo maneja el script SQL** (`spring.jpa.hibernate.ddl-auto=none`): las tablas y los datos los crea `database/socioclub.sql`, que también es un entregable.
- **MySQL en Docker** con healthcheck, para que todos trabajen con la misma base y los mismos datos.
- **Preinscripción del sitio público:** se valida del lado del cliente y muestra la confirmación, pero no se guarda. La consigna no incluye ese endpoint, y la inscripción real la hace la secretaría o el socio desde la app.

---

## Documentación

- [docs/informe/](docs/informe/): informe técnico en PDF, con la prueba de sistema y la bitácora de IA.
- [docs/plan-de-trabajo.md](docs/plan-de-trabajo.md): hitos, tareas por parte, decisiones y riesgos.
- [PRODUCT.md](PRODUCT.md) y [DESIGN.md](DESIGN.md): contexto del producto y sistema de diseño.

---

## Integrantes

| Integrante | Rol principal |
|---|---|
| _completar_ | |
| _completar_ | |
| _completar_ | |
| _completar_ | |
| _completar_ | |

---

## Uso de inteligencia artificial

Para este trabajo se usaron herramientas de IA, como permite la consigna. Todo el código fue revisado y el grupo puede explicarlo. El registro de qué se pidió, qué se obtuvo y qué se corrigió está en la **bitácora de IA**, último capítulo del informe técnico.
