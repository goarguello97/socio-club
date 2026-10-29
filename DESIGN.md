---
name: Club Social y Deportivo Los Algarrobos
description: La cartelera de letras de la secretaría del club, armada letra por letra sobre la pared verde.
colors:
  pared: "#2e6b45"
  pared-sombra: "#245738"
  pano: "#1c2620"
  ranura: "#121a15"
  letra: "#f5f3ec"
  letra-suave: "#c9d3cc"
  texto-pared: "#e1eee4"
  letra-amarilla: "#e8b931"
  letra-gastada: "#8a978f"
  aluminio: "#c3c9cb"
  aluminio-oscuro: "#8e979a"
  papel: "#ffffff"
  tinta: "#1c2620"
  tinta-suave: "#4b5a50"
  error-papel: "#b3261e"
typography:
  display:
    fontFamily: "Archivo Narrow, Arial Narrow, sans-serif"
    fontSize: "clamp(2rem, 5.5vw, 3.75rem)"
    fontWeight: 700
    lineHeight: 1.15
    letterSpacing: "0.12em"
  headline:
    fontFamily: "Archivo Narrow, Arial Narrow, sans-serif"
    fontSize: "clamp(1.6rem, 3.5vw, 2.25rem)"
    fontWeight: 700
    lineHeight: 1.1
    letterSpacing: "0.04em"
  title:
    fontFamily: "Archivo Narrow, Arial Narrow, sans-serif"
    fontSize: "1.45rem"
    fontWeight: 700
    lineHeight: "2.75rem"
    letterSpacing: "0.12em"
  numeral:
    fontFamily: "Archivo Narrow, Arial Narrow, sans-serif"
    fontSize: "2.5rem"
    fontWeight: 700
    lineHeight: 1
    fontFeature: "tnum"
  body:
    fontFamily: "Atkinson Hyperlegible, system-ui, sans-serif"
    fontSize: "1.0625rem"
    fontWeight: 400
    lineHeight: 1.6
  label:
    fontFamily: "Archivo Narrow, Arial Narrow, sans-serif"
    fontSize: "0.95rem"
    fontWeight: 700
    lineHeight: 1.1
    letterSpacing: "0.1em"
rounded:
  ficha: "3px"
  panel: "4px"
  chinche: "50%"
spacing:
  renglon: "2.75rem"
  renglon-celular: "2.25rem"
  margen: "24px"
  margen-celular: "12px"
  marco: "14px"
  marco-celular: "8px"
  seccion: "64px"
  ancho: "1180px"
components:
  button-primary:
    backgroundColor: "{colors.letra-amarilla}"
    textColor: "{colors.pano}"
    typography: "{typography.label}"
    rounded: "{rounded.ficha}"
    padding: "12px 20px"
  button-secondary:
    backgroundColor: "transparent"
    textColor: "{colors.letra}"
    typography: "{typography.label}"
    rounded: "{rounded.ficha}"
    padding: "12px 20px"
  button-secondary-planilla:
    backgroundColor: "transparent"
    textColor: "{colors.tinta}"
    rounded: "{rounded.ficha}"
    padding: "12px 20px"
  nav-ficha:
    backgroundColor: "{colors.letra}"
    textColor: "{colors.pano}"
    typography: "{typography.label}"
    rounded: "{rounded.ficha}"
    padding: "8px 14px"
  nav-ficha-actual:
    backgroundColor: "{colors.letra-amarilla}"
    textColor: "{colors.pano}"
  chip-disciplina:
    backgroundColor: "transparent"
    textColor: "{colors.letra}"
    typography: "{typography.label}"
    rounded: "{rounded.ficha}"
    padding: "7px 12px"
  chip-disciplina-elegida:
    backgroundColor: "{colors.letra-amarilla}"
    textColor: "{colors.pano}"
  input-edad:
    backgroundColor: "{colors.ranura}"
    textColor: "{colors.letra-amarilla}"
    typography: "{typography.numeral}"
    width: "3.4ch"
    padding: "2px 6px"
  input-planilla:
    backgroundColor: "{colors.papel}"
    textColor: "{colors.tinta}"
    typography: "{typography.body}"
    rounded: "{rounded.panel}"
    padding: "10px 12px"
  cartelera:
    backgroundColor: "{colors.pano}"
    textColor: "{colors.letra}"
    padding: "28px 32px 32px"
  planilla:
    backgroundColor: "{colors.papel}"
    textColor: "{colors.tinta}"
    padding: "36px 40px 32px"
    width: "760px"
  ficha-picto:
    backgroundColor: "{colors.letra}"
    rounded: "{rounded.ficha}"
    size: "calc(2.75rem - 6px)"
  pasos:
    backgroundColor: "{colors.pared-sombra}"
    textColor: "{colors.letra}"
    rounded: "{rounded.panel}"
    padding: "24px 28px"
---

# Design System: Club Social y Deportivo Los Algarrobos

## Overview

**Creative North Star: "La cartelera de letras de la secretaría"**

El sitio es la pared del club con la cartelera colgada: una pared pintada de verde, un marco de aluminio y adentro un paño negro verdoso con ranuras horizontales, donde la secretaría encastra letras de plástico blancas y amarillas para armar la semana. Todo lo que el vecino viene a buscar (días, horario, edades, cuota) se escribe con esas letras, en mayúsculas espaciadas y alineado a las ranuras. Lo que va pinchado encima (la planilla de preinscripción) es una hoja blanca de verdad, con chinches, tinta oscura y letra de lectura.

La densidad es la de una cartelera: renglones fijos y una fila por actividad, sin tarjetas flotando ni fotos. La pared verde lleva el texto corrido (la historia del club, los pasos para anotarse) en Atkinson Hyperlegible, que es la voz de lectura; las letras de plástico son la voz de anuncio. El movimiento también sale de la cartelera: cuando cambia el filtro, las filas se vuelven a "colocar" de izquierda a derecha, letra por letra, y con movimiento reducido el cambio es inmediato.

El sitio rechaza el hero con foto y la grilla de tarjetas idénticas; la información vive en el paño, no en cajas.

**Key Characteristics:**
- Tres materiales y nada más: pared verde, paño ranurado con marco de aluminio, papel blanco pinchado.
- Letras de plástico (Archivo Narrow 700, mayúsculas, espaciado ancho) para todo dato y todo anuncio; Atkinson Hyperlegible para leer.
- El amarillo marca lo que se puede usar ahora: horarios, la página actual, la disciplina elegida, la acción principal.
- Todo lo que vive sobre el paño mide un número entero de renglones (2.75rem; 2.25rem en celular dentro de la cartelera).
- Sombras suaves y físicas (letras en relieve, fichas apoyadas, hoja despegada), nunca decorativas.

## Colors

Una paleta de objetos del club: pintura verde de pared, paño casi negro, plástico blanco y amarillo, aluminio, papel y tinta.

### Primary
- **Letra amarilla** (`letra-amarilla`): la letra de plástico de destacado. Horarios y días en la semana, la bajada de cada cartelera, la cuota en las fichas, el número de la edad, la ficha de navegación de la página actual, la disciplina elegida, el botón principal, los números de los pasos, las chinches, el foco del teclado, la selección de texto y el cursor.

### Secondary
- **Pared** (`pared`): fondo de toda la página; también es el color de foco y de confirmación dentro de la planilla de papel.
- **Pared en sombra** (`pared-sombra`): un tono más oscuro de la misma pintura para el bloque "Cómo anotarte" y el carril de la barra de desplazamiento.

### Neutral
- **Paño** (`pano`): el fondo de la cartelera y de las listas de filas y fichas; también es el color del texto sobre fichas blancas y amarillas.
- **Ranura** (`ranura`): la línea de 3px al pie de cada renglón y el hueco donde se escribe la edad.
- **Letra blanca** (`letra`): la letra de plástico principal y el color del texto sobre la pared; fondo de las fichas de navegación y de los pictogramas.
- **Letra gastada clara** (`letra-suave`): datos secundarios sobre el paño (edades en la semana, rótulos de las fichas, "por mes", resultado del filtro, borde de las fichas de disciplina).
- **Texto de pared** (`texto-pared`): texto secundario sobre la pared verde (subtítulo de la marca, pie).
- **Letra gastada** (`letra-gastada`): filas que no corresponden a la edad o disciplina elegida. Es legible a propósito: en una cartelera de letras no se tacha, se deja gastada y se baja al final.
- **Aluminio / aluminio oscuro** (`aluminio`, `aluminio-oscuro`): el marco de la cartelera (degradé a 160deg desde un brillo claro) y los rieles que separan las fichas de actividades.
- **Papel / tinta / tinta suave** (`papel`, `tinta`, `tinta-suave`): la planilla de preinscripción y sus campos.
- **Error en papel** (`error-papel`): solo dentro de la planilla, para el borde del campo con error y su mensaje.

### Named Rules
**La Regla de la Letra Amarilla.** El amarillo es una letra, no una pintura de fondo: aparece en letras, fichas chicas, chinches y foco. Nunca rellena un bloque grande ni una sección.

**La Regla de los Tres Materiales.** Cada superficie es pared, paño o papel. Un elemento nuevo se decide preguntando sobre cuál de los tres está colgado, y toma sus colores de ese material (letra sobre paño, texto de pared sobre pared, tinta sobre papel).

## Typography

**Display Font:** Archivo Narrow (con Arial Narrow, sans-serif), variable 400–700, servida desde el sitio
**Body Font:** Atkinson Hyperlegible (con system-ui, sans-serif), 400 y 700, servida desde el sitio

**Character:** Archivo Narrow en 700 y mayúsculas es la letra de plástico de la cartelera: angosta, espaciada y con una sombra corta que la despega del paño. Atkinson Hyperlegible es la letra de leer, elegida por legibilidad para chicos, adultos y personas mayores.

### Hierarchy
- **Display** (700, clamp(2rem, 5.5vw, 3.75rem), 1.15, 0.12em, mayúsculas): el título de cada cartelera ("Esta semana en el club", "Actividades", "Preinscripción").
- **Bajada** (letras amarillas, clamp(1.05rem, 2vw, 1.35rem)): la línea que sigue al título dentro de la cartelera.
- **Headline** (700, clamp(1.6rem, 3.5vw, 2.25rem), 1.1, 0.04em, mayúsculas, sin sombra): títulos de sección sobre la pared y de la planilla (1.8rem).
- **Title** (700, 1.45rem, alto de renglón, 0.12em, mayúsculas): nombre de cada actividad en su ficha y la cuota.
- **Filas de la semana** (letras, 1.2rem; 1.05rem en celular, cifras tabulares): día · hora · actividad · edades · cuota.
- **Numeral** (700, 2.5rem, 1, cifras tabulares): la edad editable, en amarillo dentro de su ranura.
- **Body** (400, 1.0625rem, 1.6, máximo 65ch): texto corrido sobre la pared y en la planilla; 1.125rem en el artículo del club.
- **Label** (700, 0.95rem, 0.1em, mayúsculas): fichas de navegación, de disciplina y botones (1.05rem en el botón).

### Named Rules
**La Regla de las Dos Voces.** Las letras de plástico anuncian y dan datos; Atkinson explica. Un párrafo de más de una línea nunca va en letras de plástico, y un dato de la cartelera nunca va en Atkinson.

**La Regla de las Cifras Alineadas.** Toda columna de horarios, edades o cuotas usa cifras tabulares.

## Layout

Una columna centrada de 1180px como máximo, con 24px de margen lateral (12px en celular). Arriba, la franja de pared con el escudo y la navegación; abajo, la cartelera ocupando casi todo el ancho; después las secciones sobre la pared, separadas por 64px. "El club" usa dos columnas 3fr / 2fr con 48px de separación, que pasan a una sola en celular.

El ritmo del paño es el renglón (`--renglon`, 2.75rem; 2.25rem dentro de la cartelera en celular). Las ranuras se pintan con un degradé repetido que deja una línea de `ranura` de 3px al pie de cada renglón, y todo lo que va sobre el paño mide renglones enteros: cada fila de la semana es un renglón (dos en celular, con el área "días hora cuota" arriba y "actividad edades" abajo); cada ficha de actividad abre con un renglón de riel, un renglón de encabezado, un renglón por dato y un renglón de pie (dos en celular). El catálogo va en dos columnas con un renglón de separación vertical y 48px horizontal; una sola columna en celular.

La planilla mide 760px como máximo, con un formulario en dos columnas (22px × 24px) que pasa a una en celular; los botones de acción van a los extremos y en celular se apilan con la acción principal arriba.

Hay un solo corte, a 760px.

### Named Rules
**La Regla del Renglón.** Si algo vive sobre el paño, su alto es un múltiplo de `--renglon` y su texto apoya en la ranura. Lo que no entra en un renglón ocupa dos, nunca uno y medio.

## Elevation & Depth

La profundidad es física y chica: es la de objetos reales colgados en una pared. Las letras tienen relieve, las fichas están apoyadas, la cartelera se separa de la pared y la hoja de papel se despega un poco. Las sombras son siempre negras, difusas y hacia abajo.

### Shadow Vocabulary
- **Relieve de letra** (`text-shadow: 0 2px 2px rgba(0,0,0,0.45)`): toda letra de plástico sobre el paño; se quita en las filas gastadas.
- **Ficha apoyada** (`box-shadow: 0 2px 4px rgba(0,0,0,0.35)`): fichas de navegación; los pictogramas usan `0 2px 2px rgba(0,0,0,0.45)`.
- **Botón** (`box-shadow: 0 3px 6px rgba(0,0,0,0.4)`, en hover `0 6px 10px rgba(0,0,0,0.4)` con subida de 2px).
- **Cartelera colgada** (`box-shadow: 0 18px 40px rgba(0,0,0,0.35), inset 0 0 0 1px rgba(0,0,0,0.6)`).
- **Hoja pinchada** (`box-shadow: 0 12px 26px rgba(0,0,0,0.45)`), girada -0.4deg en escritorio y derecha en celular.
- **Hueco de la edad** (`box-shadow: inset 0 2px 6px rgba(0,0,0,0.6)`): la única sombra hacia adentro, porque es una ranura.
- **Escudo** (`filter: drop-shadow(0 3px 4px rgba(0,0,0,0.35))`).

### Named Rules
**La Regla del Objeto Colgado.** Una sombra solo existe si el objeto existiría en la pared del club. Las sombras son difusas y hacia abajo; nada flota sin motivo.

## Shapes

Las fichas de plástico tienen esquinas apenas redondeadas (3px): navegación, disciplinas, botones, pictogramas y números de paso. Los paneles sobre la pared y los campos de la planilla usan 4px. La cartelera y la hoja de papel son rectas, sin redondeo; la cartelera lleva un marco de aluminio de 14px (8px en celular). Las chinches son el único círculo. Las fichas de actividad se separan con un riel de aluminio de 4px dentro de su primer renglón, no con bordes ni cajas.

## Components

### Buttons
Fichas amarillas grandes, encastradas en la cartelera.
- **Shape:** esquinas apenas redondeadas (3px), sin borde.
- **Primary:** fondo letra amarilla, texto paño, letras 1.05rem con 0.1em, padding 12px 20px.
- **Hover / Focus:** sube 2px y la sombra crece (0.15s ease-out); al presionar vuelve a su lugar. Foco: contorno amarillo de 3px a 3px de distancia.
- **Secondary:** transparente con contorno interior de 2px en letra blanca; dentro de la planilla, contorno y texto en tinta.
- **En la ficha:** el botón "Preinscribirme" se dibuja dentro del renglón (alto de renglón menos 10px), pero su área tocable mide 44px de alto.

### Chips
- **Style:** fichas de disciplina transparentes, borde de 2px en letra gastada clara, texto letra blanca, padding 7px 12px; en hover el borde pasa a letra blanca.
- **State:** la elegida (`aria-pressed="true"`) queda encastrada en amarillo con texto paño.

### Cards / Containers
- **Cartelera:** el contenedor firma; paño ranurado, marco de aluminio, padding 28px 32px 32px (20px 16px 22px en celular).
- **Ficha de actividad:** no es una tarjeta; es un tramo del paño separado por un riel de aluminio, con pictograma, nombre, lista de datos (rótulo en 0.85rem letra gastada clara, valor en letra blanca) y pie con cuota amarilla y botón.
- **Pasos:** panel de pared en sombra, 4px, padding 24px 28px, con números en fichas amarillas de 2.2rem.

### Inputs / Fields
- **Edad (sobre el paño):** hueco de ranura con sombra interior, borde inferior amarillo de 3px, número amarillo de 2.5rem, sin flechas; marcador "--" en letra gastada clara.
- **Planilla (sobre el papel):** fondo papel, borde 2px gris verdoso (#9aa59e), 4px, padding 10px 12px, tipografía de lectura; rótulos en 700 con aclaraciones en tinta suave.
- **Focus:** contorno de 3px en pared, a 1px, y el borde también pasa a pared.
- **Error:** borde en error de papel y mensaje en 0.95rem 700 debajo del campo, dentro del mismo espacio; nunca `alert()`.

### Navigation
Fichas blancas sueltas sobre la pared: letra blanca de fondo, texto paño, letras 0.95rem con 0.1em, padding 8px 14px (7px 10px y 0.85rem en celular), 3px. En hover suben 2px; la página actual va en amarillo. Al lado, la marca: escudo de 52px y nombre en letras con subtítulo en texto de pared.

### Pictogramas
Pictogramas de línea propios (trazo 3 en paño, viewBox 48) montados en una ficha blanca de un renglón menos 6px, ocupando el 75%.

### Colocar (movimiento firma)
Al cambiar edad o disciplina, la lista se reordena (primero lo que corresponde, después lo gastado) y cada fila vuelve a colocarse con un recorte de izquierda a derecha en 16 pasos (0.55s, 60ms de demora por orden). La animación solo se aplica durante la espera y el recorrido, para no recortar el área tocable. Con `prefers-reduced-motion: reduce` no hay animación.

## Do's and Don'ts

### Do:
- **Do** poner todo dato de actividad (día, horario, edades, cuota, cupo) en letras de plástico sobre el paño, con cifras tabulares y alineado al renglón.
- **Do** usar la letra amarilla para lo que se puede usar ahora: horario, página actual, disciplina elegida, acción principal, foco.
- **Do** mostrar lo que no corresponde con letra gastada y al final, en vez de ocultarlo o tacharlo.
- **Do** escribir los formularios en la hoja de papel pinchada, con tinta oscura y errores junto al campo.
- **Do** dejar 44px de área tocable aunque la ficha dibujada sea más baja.
- **Do** respetar `prefers-reduced-motion` en toda animación de colocación.

### Don't:
- **Don't** usar fotos de héroe ni grillas de tarjetas idénticas con sombra; la información vive en el paño.
- **Don't** rellenar bloques grandes de amarillo ni usar el rojo de error fuera del papel.
- **Don't** escribir párrafos largos en letras de plástico ni datos de la cartelera en Atkinson.
- **Don't** romper el renglón: nada sobre el paño mide medio renglón.
- **Don't** agregar sombras que no correspondan a un objeto colgado en la pared.

### Fuera de alcance
El sistema de secretaría (`backend/src/main/resources/static/css/backoffice.css`, Thymeleaf) tiene una hoja propia, anterior y mínima, que todavía no sigue este mundo. Queda pendiente de alineación: según PRODUCT.md hereda esta identidad "con más sobriedad", pero hoy no forma parte de este sistema.
