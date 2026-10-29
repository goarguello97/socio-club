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
| [Python](https://www.python.org/) | 3.12 | Servidor local del sitio público (o la extensión Live Server de VS Code) |
| [Node.js](https://nodejs.org/) | 20 LTS recomendado | App React de socios (en desarrollo) |

### 1. Base de datos

Desde la raíz del repositorio:

```bash
docker compose up -d
```

Levanta MySQL 8 en el puerto **3307** (para no chocar con un MySQL local en el 3306). La primera vez crea las tablas y carga los datos iniciales desde [database/socioclub.sql](database/socioclub.sql).

Para **volver a los datos iniciales** (borra todo lo que se haya cargado):

```bash
docker compose down -v
```

```bash
docker compose up -d
```

### 2. Backend (sistema de secretaría)

En Windows (PowerShell):

```powershell
cd backend; .\mvnw spring-boot:run
```

En macOS o Linux:

```bash
cd backend && ./mvnw spring-boot:run
```

Cuando aparece `Started SocioclubApplication`, el sistema de secretaría queda en **http://localhost:8080**.

> Si el backend falla con `Communications link failure`, la base todavía no terminó de arrancar: esperá unos segundos y volvé a ejecutarlo.

### 3. Sitio público

Hay que servirlo con un servidor local: si se abre con doble clic (`file://`) no cargan los estilos ni los scripts. Desde la raíz del repositorio:

```bash
python -m http.server 5500 --directory sitio-publico
```

El sitio queda en **http://localhost:5500**. También se puede abrir `sitio-publico/index.html` con la extensión **Live Server** de VS Code.

### 4. App "Mi Club" para socios

En desarrollo (Semana 2 y 3 del plan). La carpeta [app-socios/](app-socios/) todavía no tiene código.

### Direcciones y puertos

| Servicio | URL / puerto |
|---|---|
| Sitio público | http://localhost:5500 |
| Sistema de secretaría | http://localhost:8080 |
| API REST | http://localhost:8080/api (en desarrollo) |
| App de socios | http://localhost:3000 (en desarrollo) |
| MySQL | `localhost:3307` · base `socioclub` · usuario `socioclub` · contraseña `socioclub123` |

---

## Datos de prueba

La base arranca con las 8 actividades del enunciado y **20 socios de prueba**, pensados para poder demostrar cada regla de negocio. Todos los datos son ficticios.

| DNI | Socio | Edad aprox. | Cuota | Para demostrar |
|---|---|---|---|---|
| `31456789` | Lucía Gómez | 41 | Al día | **RN5**: ya tiene 3 actividades (Yoga, Funcional y Natación) |
| `38901234` | Marcos López | 31 | **Moroso** | **RN4**: no puede inscribirse a nada nuevo |
| `30456123` | Gabriel Torres | 43 | **Moroso** | **RN4** |
| `52345678` | Mateo Castro | 14 | Al día | **RN3**: no entra en Fútbol infantil (6 a 12) |
| `35123456` | Juan Pereyra | 36 | Al día | **RN2**: ya está en Vóley mixto, si se lo inscribe de nuevo |
| `45901234` | Nicolás Vega | 22 | Al día | Sin actividades (pantalla vacía en la app) y último lugar de Yoga |
| `54789012` | Tomás Herrera | 12 | Al día | Caso exitoso de un chico (Fútbol infantil y Natación) |

- **RN1 (cupo):** Yoga tiene **14 de 15** lugares ocupados. La primera inscripción nueva entra y la siguiente se rechaza.
- **RN7 (DNI):** se valida en el formulario del sitio. `35.123.456` (con puntos), `ABC12345`, `123456` (6 dígitos) y `123456789` (9 dígitos) se rechazan.

El resto de los socios está en [database/socioclub.sql](database/socioclub.sql).

---

## Qué es

El club tiene alrededor de 650 socios y 8 actividades: fútbol infantil, vóley, básquet, natación, patín artístico, ajedrez, funcional y yoga. Hoy toda la gestión es manual, con cupos excedidos, inscripciones duplicadas, edades fuera de rango y socios morosos que se siguen anotando. El sistema tiene **tres caras sobre una sola base de datos y un solo backend**:

| Componente | Para quién | Tecnología | Carpeta | Estado |
|---|---|---|---|---|
| A. Sitio público | Vecinos | HTML5, CSS3, JavaScript | [sitio-publico/](sitio-publico/) | ✅ |
| B. Sistema de secretaría | Secretaría y Comisión Directiva | Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA, MySQL | [backend/](backend/) | 🔄 ABM de actividades listo |
| C. API REST | App de socios | Spring Boot `@RestController`, JSON | [backend/](backend/) | ⬜ |
| D. App "Mi Club" | Socios | React, React Router, Axios | [app-socios/](app-socios/) | ⬜ |
| E. Pruebas | El equipo | Jest, React Testing Library, Postman | — | ⬜ |

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

Las reglas de negocio se escriben **una sola vez**, en la capa de servicio. Una inscripción que entra por el sistema de secretaría o por la app de socios se valida exactamente igual.

### Reglas de negocio

| Regla | Qué controla | Dónde se implementa |
|---|---|---|
| RN1 | Una actividad no puede superar su cupo máximo de inscripciones activas | Servicio (backend) |
| RN2 | Un socio no puede estar dos veces en la misma actividad | Servicio (backend) |
| RN3 | La edad del socio tiene que estar dentro del rango de la actividad | Servicio (backend) |
| RN4 | Un socio moroso no puede inscribirse a actividades nuevas | Servicio (backend) |
| RN5 | Máximo 3 actividades activas por socio | Servicio (backend) |
| RN6 | Cuota estimada: suma de cuotas, con 10 % de descuento con 2 actividades y 15 % con 3 | Función JavaScript probada con Jest |
| RN7 | DNI de 7 u 8 dígitos, sin puntos | JavaScript del lado del cliente |

---

## Estructura del repositorio

```
├── backend/                  Spring Boot (Maven): secretaría + API REST
│   └── src/main/
│       ├── java/ar/edu/siglo21/socioclub/
│       │   ├── model/        Entidades JPA: Socio, Actividad, Inscripcion y enums
│       │   ├── repository/   Interfaces JpaRepository (métodos derivados y @Query)
│       │   ├── service/      Reglas de negocio y excepciones
│       │   └── controller/   Controladores MVC (Thymeleaf)
│       └── resources/
│           ├── templates/    Vistas Thymeleaf y fragmentos comunes
│           ├── static/css/   Estilos del sistema de secretaría
│           └── application.properties
├── sitio-publico/            index.html, actividades.html, preinscripcion.html
│   ├── css/estilos.css
│   ├── js/script.js          Clase Actividad, catálogo, filtros y validación
│   ├── img/                  Escudo y pictogramas (SVG propios)
│   └── fonts/                Tipografías servidas desde el sitio
├── app-socios/               App React (en desarrollo)
├── database/socioclub.sql    Creación de tablas y datos iniciales
├── docs/plan-de-trabajo.md   Hitos, tareas por parte, decisiones y riesgos
├── docker-compose.yml        MySQL 8
├── PRODUCT.md                Contexto del producto (usuarios, reglas, restricciones)
└── DESIGN.md                 Sistema de diseño del sitio público
```

---

## Decisiones técnicas

- **Spring Boot 3.5 con Java 17.** Spring Initializr ya ofrece solo la versión 4. Se usó la última 3.x para mantener `spring-boot-starter-web` y Jackson 2, como en las lecturas de la materia.
- **Solo tecnologías de la materia.** Sin Lombok, Spring Security, Vite, Tailwind ni Redux. Getters y setters escritos a mano.
- **El esquema lo maneja el script SQL** (`spring.jpa.hibernate.ddl-auto=none`): las tablas y los datos los crea `database/socioclub.sql`, que también es un entregable.
- **MySQL en Docker:** todos los integrantes trabajan con la misma base y los mismos datos con un solo comando.
- **Inyección por constructor** en servicios y controladores. Los controladores nunca acceden a los repositorios.
- **Errores de negocio como excepciones:** el servicio lanza `ReglaNegocioException` con el código de la regla. La vista Thymeleaf la muestra como mensaje y la API la va a devolver como `409 Conflict`.
- **Sitio público sin frameworks:** el catálogo se genera con JavaScript desde un arreglo de objetos `Actividad`, y las tipografías se sirven desde el propio sitio, así que funciona sin internet.

---

## Documentación

- [docs/plan-de-trabajo.md](docs/plan-de-trabajo.md): hitos, tareas por parte con responsable y estado, decisiones abiertas y riesgos.
- [PRODUCT.md](PRODUCT.md): usuarios, propósito, reglas y restricciones del producto.
- [DESIGN.md](DESIGN.md): sistema de diseño del sitio público ("la cartelera de la secretaría").

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
