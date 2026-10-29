# Product

<!-- impeccable:product-schema 1 -->

## Platform

web

## Stack

Impuesto por la cátedra (Taller de Construcción de Software, Universidad Siglo 21). No usar herramientas fuera de la materia: nada de Vite, Tailwind, Lombok, Spring Security, Redux ni `fetch` en la app React.

- `/sitio-publico`: HTML5 semántico, una hoja CSS3 externa y JavaScript sin frameworks (clase `Actividad`, DOM, `addEventListener`).
- `/backend`: Spring Boot 3.5 con Thymeleaf (sistema de secretaría), Spring Data JPA, MySQL 8 en Docker y API REST bajo `/api`.
- `/app-socios`: React creado con create-react-app, con React Router, Axios, Context y PropTypes.

## Users

- **Vecinos de la zona norte de Córdoba (público principal de la identidad).** Entran al sitio público, muchas veces desde el celular, para saber qué actividades hay, en qué horario, para qué edades y cuánto cuestan. Pueden dejar una preinscripción. Hoy saturan la secretaría con esas consultas por teléfono y WhatsApp.
- **Marta, la secretaria.** Usa el sistema de secretaría en la PC del club durante toda la jornada: carga socios y actividades, inscribe gente y controla cupos. Necesita que el sistema le impida pasarse de cupo o de edad.
- **Socios (unos 650).** Entran a la app "Mi Club" desde el celular con su DNI. Ven a qué están anotados y cuánto pagan, y pueden anotarse o darse de baja solos.
- **Docente evaluador.** Ve el sistema en una exposición oral en vivo y puede pedir cambios pequeños en el código.

## Product Purpose

SocioClub reemplaza la gestión en cuaderno, Excel y WhatsApp del Club Social y Deportivo Los Algarrobos. Tiene tres caras sobre una sola base de datos: un sitio público, un sistema de secretaría y una app para socios. Hay éxito cuando:

- no hay cupos excedidos, inscripciones duplicadas ni edades fuera de rango;
- ningún socio moroso se anota a una actividad nueva;
- los vecinos encuentran solos la información que hoy preguntan en secretaría.

Además es un trabajo práctico integrador: cada integrante del grupo tiene que poder explicar y defender cada parte.

## Positioning

Es un club de barrio real y concreto, no una cadena de gimnasios: 8 actividades para chicos y adultos, profesores con nombre y apellido, y cuotas en pesos. Las reglas de negocio (RN1 a RN5) se escriben una sola vez en la capa de servicio y valen igual si la inscripción entra por secretaría o por la app.

## Operating Context

- El sitio público se ve sobre todo en celulares; el filtro por disciplina y edad y el formulario de preinscripción tienen que funcionar bien a ese ancho.
- El sistema de secretaría se usa en escritorio, con tablas, formularios y mensajes de error de las reglas.
- La app de socios es para celular; el socio entra con DNI de 7 u 8 dígitos sin puntos (RN7).
- La demostración es en vivo en la computadora del grupo, con backend, base y app levantados.

## Capabilities and Constraints

- **Actividades fijas del enunciado:** Fútbol infantil, Vóley mixto, Básquet juvenil, Natación, Patín artístico, Ajedrez, Funcional adultos y Yoga, con días, horario, edades, cupo, cuota y profesor/a definidos (ver `database/socioclub.sql`).
- **Reglas de negocio:**
  - RN1: cupo máximo.
  - RN2: sin inscripciones duplicadas.
  - RN3: edad dentro del rango de la actividad.
  - RN4: un socio moroso no se inscribe.
  - RN5: máximo 3 actividades por socio.
  - RN6: bonificación del 10 % con 2 actividades y del 15 % con 3.
  - RN7: DNI válido.
- **Sitio público:** tres páginas como mínimo (`index.html`, `actividades.html`, `preinscripcion.html`). El catálogo se genera con JavaScript desde un arreglo, y los errores de validación se muestran junto al campo, nunca con `alert()`.
- **Diseño responsivo:** obligatorio, con al menos una media query; en celular las tarjetas de actividades van en una columna.
- **Decisión abierta:** si la preinscripción del sitio público se envía a un endpoint o solo se valida en el cliente.

## Brand Commitments

- **Nombre:** Club Social y Deportivo Los Algarrobos, un club de barrio de la zona norte de la ciudad de Córdoba.
- **Recursos de marca:** el club no tiene logo, colores ni escudo propios. Se crean desde cero, coherentes con un club de barrio cordobés. El algarrobo, árbol nativo de la región, es una referencia natural del nombre.
- **Voz:** cercana y de barrio, con trato de vos, como un club donde todos se conocen. Clara y directa en las reglas ("La actividad no tiene cupo disponible").
- **Identidad:** la marca la define la cara pública (el sitio para vecinos). El sistema de secretaría y la app heredan esa identidad con más sobriedad.

## Evidence on Hand

- **Datos reales del enunciado:** actividades, horarios, edades, cupos, cuotas y profesores (`database/socioclub.sql`).
- **Historia del caso:** unos 650 socios. En marzo se anotaron 52 chicos en fútbol infantil, que tiene cupo para 40. La secretaria hoy trabaja con cuaderno y Excel.
- **Lo que no existe y no hay que inventar:** fotos reales del club, testimonios, año de fundación, dirección exacta, teléfonos y redes sociales. Si hace falta historia o ubicación para `index.html`, se marca como texto de ejemplo para que el grupo lo complete.

## Product Principles

1. La información de las actividades (día, horario, edades, cuota, cupo) es lo que el vecino viene a buscar; tiene que encontrarse sin esfuerzo.
2. Las reglas protegen al club: cuando una operación se rechaza, el mensaje dice exactamente por qué y qué hacer.
3. Todo tiene que poder explicarse en la defensa: preferir la solución que el grupo entienda por sobre la más ingeniosa.
4. Calidez de club de barrio, sin perder la claridad de un sistema que maneja cupos y cuotas.

## Accessibility & Inclusion

- **Usuarios:** chicos (a través de sus familias), adultos y personas mayores, con socios de hasta 70 años o más en natación, ajedrez y yoga. Hace falta texto legible, buen contraste y objetivos táctiles cómodos en celular.
- **Requisitos de la cátedra:** imágenes con `alt`, encabezados jerárquicos y HTML semántico usado con sentido.
