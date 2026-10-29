/* =====================================================================
   Club Los Algarrobos — sitio público
   Un solo archivo para las tres páginas. Cada página tiene un id en el
   <body> y al final se inicializa solo lo que esa página necesita.
   ===================================================================== */

// ---------------------------------------------------------------------
// Clase Actividad
// ---------------------------------------------------------------------

class Actividad {
    constructor(id, nombre, disciplina, dias, horario, edadMinima, edadMaxima, cupoMaximo, cuotaMensual, profesor, icono) {
        this.id = id;
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.dias = dias;               // arreglo, por ejemplo ["Lun", "Mié"]
        this.horario = horario;         // "18:00"
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
        this.cupoMaximo = cupoMaximo;
        this.cuotaMensual = cuotaMensual;
        this.profesor = profesor;
        this.icono = icono;             // nombre del pictograma en img/pictos
    }

    /** true si una persona de esa edad puede anotarse (misma regla que RN3 del backend). */
    esAptaParaEdad(edad) {
        return edad >= this.edadMinima && edad <= this.edadMaxima;
    }

    /** true si la actividad es de la disciplina pedida ("Todas" acepta cualquiera). */
    esDeDisciplina(disciplina) {
        return disciplina === "Todas" || this.disciplina === disciplina;
    }

    getDiasTexto() {
        if (this.dias.length === 1) {
            return this.dias[0];
        }
        return this.dias.slice(0, -1).join(", ") + " y " + this.dias[this.dias.length - 1];
    }

    getRangoEdades() {
        return this.edadMinima + " a " + this.edadMaxima;
    }

    getCuotaFormateada() {
        return "$ " + this.cuotaMensual.toLocaleString("es-AR");
    }

    /** Número del primer día de la semana en que se dicta (para ordenar la cartelera). */
    getOrdenSemana() {
        const ordenDias = ["Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"];
        return ordenDias.indexOf(this.dias[0]) * 100 + parseInt(this.horario, 10);
    }
}

// ---------------------------------------------------------------------
// Datos: las 8 actividades del club.
// DATOS DE EJEMPLO, ficticios: salen del enunciado del TP y son iguales a los
// de database/socioclub.sql (profes, cuotas y cupos no son de un club real).
// ---------------------------------------------------------------------

const actividades = [
    new Actividad(1, "Fútbol infantil", "Fútbol", ["Lun", "Mié"], "18:00", 6, 12, 40, 18000, "Diego Ferreyra", "futbol"),
    new Actividad(2, "Vóley mixto", "Vóley", ["Mar", "Jue"], "19:00", 13, 60, 24, 20000, "Laura Ceballos", "voley"),
    new Actividad(3, "Básquet juvenil", "Básquet", ["Lun", "Vie"], "19:30", 12, 17, 20, 19000, "Martín Oviedo", "basquet"),
    new Actividad(4, "Natación", "Natación", ["Mar", "Jue"], "17:00", 5, 70, 30, 26000, "Sofía Bustos", "natacion"),
    new Actividad(5, "Patín artístico", "Patín", ["Mié", "Vie"], "17:30", 5, 15, 25, 21000, "Carla Peralta", "patin"),
    new Actividad(6, "Ajedrez", "Ajedrez", ["Sáb"], "10:00", 8, 99, 16, 9000, "Raúl Giménez", "ajedrez"),
    new Actividad(7, "Funcional adultos", "Fitness", ["Lun", "Mié", "Vie"], "20:30", 18, 65, 30, 22000, "Paula Luna", "fitness"),
    new Actividad(8, "Yoga", "Bienestar", ["Mar", "Jue"], "09:00", 16, 99, 15, 17000, "Inés Arguello", "yoga")
];

// ---------------------------------------------------------------------
// Utilidades compartidas
// ---------------------------------------------------------------------

// Disciplina elegida en las fichas de filtro (inicio y actividades)
let disciplinaElegida = "Todas";

/** Crea un elemento con clase y texto en una sola línea. */
function crearElemento(etiqueta, clase, texto) {
    const elemento = document.createElement(etiqueta);
    if (clase) {
        elemento.className = clase;
    }
    if (texto !== undefined) {
        elemento.textContent = texto;
    }
    return elemento;
}

/** Lee la edad de un input. Devuelve null si está vacío o no es un número válido. */
function leerEdad(input) {
    const valor = input.value.trim();
    if (valor === "") {
        return null;
    }
    const edad = Number(valor);
    if (!Number.isInteger(edad) || edad < 0 || edad > 120) {
        return null;
    }
    return edad;
}

/** Vuelve a disparar la animación "colocar" sobre un elemento. */
function animarColocacion(elemento, orden) {
    elemento.style.setProperty("--orden", orden);
    elemento.classList.remove("colocando");
    void elemento.offsetWidth; // fuerza al navegador a reiniciar la animación
    elemento.classList.add("colocando");
}

function buscarActividadPorId(id) {
    return actividades.find(function (actividad) {
        return actividad.id === id;
    });
}

// ---------------------------------------------------------------------
// Página de inicio: la semana del club en la cartelera
// ---------------------------------------------------------------------

function crearFilaSemana(actividad) {
    const fila = crearElemento("li", "fila letras");
    fila.dataset.id = actividad.id;
    fila.appendChild(crearElemento("span", "dias", actividad.dias.join(" ")));
    fila.appendChild(crearElemento("span", "hora", actividad.horario));
    fila.appendChild(crearElemento("span", "actividad", actividad.nombre));
    fila.appendChild(crearElemento("span", "edades", actividad.getRangoEdades()));
    fila.appendChild(crearElemento("span", "cuota", actividad.getCuotaFormateada()));
    return fila;
}

/** true si la actividad corresponde a la edad y la disciplina elegidas (edad null = cualquiera). */
function corresponde(actividad, edad, disciplina) {
    const pasaEdad = edad === null || actividad.esAptaParaEdad(edad);
    return pasaEdad && actividad.esDeDisciplina(disciplina);
}

/** Arma la cartelera: primero las actividades que te corresponden, después el resto con letra gastada. */
function mostrarSemana(edad, disciplina) {
    const lista = document.getElementById("semana");
    lista.innerHTML = "";

    const ordenadas = actividades.slice().sort(function (a, b) {
        const diferencia = Number(corresponde(b, edad, disciplina)) - Number(corresponde(a, edad, disciplina));
        if (diferencia !== 0) {
            return diferencia;
        }
        return a.getOrdenSemana() - b.getOrdenSemana();
    });

    ordenadas.forEach(function (actividad, indice) {
        const fila = crearFilaSemana(actividad);
        if (!corresponde(actividad, edad, disciplina)) {
            fila.classList.add("fuera-de-edad");
            fila.setAttribute("aria-label", actividad.nombre + ": no corresponde a lo que elegiste");
        }
        lista.appendChild(fila);
        animarColocacion(fila, indice);
    });
}

function iniciarPaginaInicio() {
    const inputEdad = document.getElementById("edad");
    const inputDisciplina = document.getElementById("disciplina-elegida");

    function rearmar() {
        inputDisciplina.value = disciplinaElegida === "Todas" ? "" : disciplinaElegida;
        inputDisciplina.disabled = disciplinaElegida === "Todas"; // así no viaja vacía en la URL
        mostrarSemana(leerEdad(inputEdad), disciplinaElegida);
    }

    crearBotonesDisciplina(rearmar);
    rearmar();

    // Cada vez que cambia la edad, la cartelera se vuelve a armar
    inputEdad.addEventListener("input", rearmar);
    // Al enviar, el formulario va a actividades.html?edad=N&disciplina=X (method="get")
}

// ---------------------------------------------------------------------
// Página de actividades: catálogo con filtro por disciplina y por edad
// ---------------------------------------------------------------------

function crearFicha(actividad) {
    const ficha = crearElemento("article", "ficha");

    const encabezado = crearElemento("header", "ficha-encabezado");
    const picto = crearElemento("span", "ficha-picto");
    const icono = document.createElement("img");
    icono.src = "img/pictos/" + actividad.icono + ".svg";
    icono.alt = "Pictograma de " + actividad.disciplina.toLowerCase();
    icono.width = 30;
    icono.height = 30;
    picto.appendChild(icono);
    encabezado.appendChild(picto);
    encabezado.appendChild(crearElemento("h2", "letras", actividad.nombre));
    ficha.appendChild(encabezado);

    const datos = crearElemento("dl", "letras");
    const filas = [
        ["Días", actividad.getDiasTexto()],
        ["Horario", actividad.horario + " h"],
        ["Edades", actividad.getRangoEdades() + " años"],
        ["Profe", actividad.profesor],
        ["Cupo", actividad.cupoMaximo + " lugares"]
    ];
    filas.forEach(function (par) {
        datos.appendChild(crearElemento("dt", null, par[0]));
        datos.appendChild(crearElemento("dd", null, par[1]));
    });
    ficha.appendChild(datos);

    const pie = crearElemento("footer", "ficha-pie");
    const cuota = crearElemento("p", "ficha-cuota letras amarilla", actividad.getCuotaFormateada());
    cuota.appendChild(crearElemento("small", null, " por mes"));
    const enlace = crearElemento("a", "boton", "Preinscribirme");
    enlace.href = "preinscripcion.html?actividad=" + actividad.id;
    enlace.setAttribute("aria-label", "Preinscribirme en " + actividad.nombre);
    pie.appendChild(cuota);
    pie.appendChild(enlace);
    ficha.appendChild(pie);

    return ficha;
}

function aplicarFiltros() {
    const edad = leerEdad(document.getElementById("edad"));
    const contenedor = document.getElementById("lista-actividades");
    const resultado = document.getElementById("resultado-filtro");

    const visibles = actividades.filter(function (actividad) {
        return corresponde(actividad, edad, disciplinaElegida);
    });

    contenedor.innerHTML = "";
    if (visibles.length === 0) {
        contenedor.appendChild(crearElemento("p", "sin-resultados letras",
            "No hay actividades para esa edad en " + disciplinaElegida.toLowerCase()));
    } else {
        visibles.forEach(function (actividad, indice) {
            const ficha = crearFicha(actividad);
            contenedor.appendChild(ficha);
            animarColocacion(ficha, indice);
        });
    }

    let texto = "Mostrando " + visibles.length + " de " + actividades.length + " actividades";
    if (edad !== null) {
        texto += " para " + edad + " años";
    }
    resultado.textContent = texto;

    // Guardamos el filtro en la URL sin recargar (objeto history), así se puede compartir el enlace
    const parametros = new URLSearchParams();
    if (edad !== null) {
        parametros.set("edad", edad);
    }
    if (disciplinaElegida !== "Todas") {
        parametros.set("disciplina", disciplinaElegida);
    }
    const consulta = parametros.toString();
    history.replaceState(null, "", consulta ? "?" + consulta : location.pathname);
}

/** Crea una ficha por disciplina; al elegir una, llama a alCambiar para redibujar la página. */
function crearBotonesDisciplina(alCambiar) {
    const contenedor = document.getElementById("disciplinas");
    const disciplinas = ["Todas"];
    actividades.forEach(function (actividad) {
        if (!disciplinas.includes(actividad.disciplina)) {
            disciplinas.push(actividad.disciplina);
        }
    });

    disciplinas.forEach(function (disciplina) {
        const boton = crearElemento("button", "ficha-disciplina", disciplina);
        boton.type = "button";
        boton.setAttribute("aria-pressed", disciplina === disciplinaElegida ? "true" : "false");
        boton.addEventListener("click", function () {
            disciplinaElegida = disciplina;
            contenedor.querySelectorAll("button").forEach(function (otro) {
                otro.setAttribute("aria-pressed", otro === boton ? "true" : "false");
            });
            alCambiar();
        });
        contenedor.appendChild(boton);
    });
}

function iniciarPaginaActividades() {
    // Si venimos desde el inicio (actividades.html?edad=10), leemos la URL con el objeto location
    const parametros = new URLSearchParams(location.search);
    const inputEdad = document.getElementById("edad");
    if (parametros.has("edad")) {
        inputEdad.value = parametros.get("edad");
    }
    if (parametros.has("disciplina")) {
        disciplinaElegida = parametros.get("disciplina");
    }

    crearBotonesDisciplina(aplicarFiltros);
    aplicarFiltros();
    inputEdad.addEventListener("input", aplicarFiltros);
}

// ---------------------------------------------------------------------
// Página de preinscripción: formulario validado con JavaScript
// ---------------------------------------------------------------------

/** RN7: el DNI tiene 7 u 8 dígitos numéricos, sin puntos. */
function esDniValido(dni) {
    return /^\d{7,8}$/.test(dni);
}

function esEmailValido(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/.test(email);
}

function calcularEdad(fechaNacimiento) {
    const hoy = new Date();
    let edad = hoy.getFullYear() - fechaNacimiento.getFullYear();
    const todaviaNoCumplio = hoy.getMonth() < fechaNacimiento.getMonth()
        || (hoy.getMonth() === fechaNacimiento.getMonth() && hoy.getDate() < fechaNacimiento.getDate());
    if (todaviaNoCumplio) {
        edad--;
    }
    return edad;
}

/** Cada función devuelve el mensaje de error del campo, o "" si está bien. */
const validaciones = {
    nombre: function (valor) {
        return valor === "" ? "Escribí tu nombre." : "";
    },
    apellido: function (valor) {
        return valor === "" ? "Escribí tu apellido." : "";
    },
    dni: function (valor) {
        if (valor === "") {
            return "Escribí tu DNI.";
        }
        if (!esDniValido(valor)) {
            return "El DNI va sin puntos: 7 u 8 números, por ejemplo 35123456.";
        }
        return "";
    },
    nacimiento: function (valor) {
        if (valor === "") {
            return "Elegí tu fecha de nacimiento.";
        }
        if (new Date(valor + "T00:00") > new Date()) {
            return "La fecha de nacimiento no puede ser futura.";
        }
        return "";
    },
    email: function (valor) {
        if (valor === "") {
            return "Escribí tu email para que te podamos contestar.";
        }
        if (!esEmailValido(valor)) {
            return "Revisá el email: tiene que ser como nombre@correo.com.";
        }
        return "";
    },
    actividad: function (valor, formulario) {
        if (valor === "") {
            return "Elegí la actividad que te interesa.";
        }
        // Si ya sabemos la fecha de nacimiento, chequeamos la edad (misma idea que RN3)
        const nacimiento = formulario.elements.nacimiento.value;
        if (nacimiento !== "" && validaciones.nacimiento(nacimiento) === "") {
            const actividad = buscarActividadPorId(Number(valor));
            const edad = calcularEdad(new Date(nacimiento + "T00:00"));
            if (!actividad.esAptaParaEdad(edad)) {
                return actividad.nombre + " es de " + actividad.getRangoEdades()
                    + " años y según la fecha de nacimiento tenés " + edad + ".";
            }
        }
        return "";
    }
};

/** Muestra (o borra) el error al lado del campo. Devuelve true si el campo es válido. */
function validarCampo(formulario, nombreCampo) {
    const campo = formulario.elements[nombreCampo];
    const mensaje = validaciones[nombreCampo](campo.value.trim(), formulario);
    const contenedor = campo.closest(".campo");
    const error = document.getElementById("error-" + nombreCampo);

    error.textContent = mensaje;
    contenedor.classList.toggle("con-error", mensaje !== "");
    campo.setAttribute("aria-invalid", mensaje !== "" ? "true" : "false");
    return mensaje === "";
}

function cargarOpcionesActividad(select) {
    actividades.forEach(function (actividad) {
        const opcion = document.createElement("option");
        opcion.value = actividad.id;
        opcion.textContent = actividad.nombre + " · " + actividad.getDiasTexto() + " " + actividad.horario
            + " · " + actividad.getRangoEdades() + " años";
        select.appendChild(opcion);
    });
}

function mostrarConfirmacion(formulario) {
    const datos = formulario.elements;
    const actividad = buscarActividadPorId(Number(datos.actividad.value));
    const confirmacion = document.getElementById("confirmacion");

    document.getElementById("nombre-confirmado").textContent = datos.nombre.value.trim();
    document.getElementById("actividad-confirmada").textContent =
        actividad.nombre + " (" + actividad.getDiasTexto() + " " + actividad.horario + ", " + actividad.getCuotaFormateada() + " por mes)";
    document.getElementById("email-confirmado").textContent = datos.email.value.trim();

    formulario.hidden = true;
    confirmacion.hidden = false;
    confirmacion.querySelector("h2").focus();
}

function iniciarPaginaPreinscripcion() {
    const formulario = document.getElementById("form-preinscripcion");
    const select = formulario.elements.actividad;
    cargarOpcionesActividad(select);

    // Si venimos desde una ficha (preinscripcion.html?actividad=3), la dejamos elegida
    const parametros = new URLSearchParams(location.search);
    if (parametros.has("actividad") && buscarActividadPorId(Number(parametros.get("actividad")))) {
        select.value = parametros.get("actividad");
    }

    const campos = Object.keys(validaciones);

    // Validación de cada campo al salir de él, y limpieza del error mientras se corrige
    campos.forEach(function (nombreCampo) {
        const campo = formulario.elements[nombreCampo];
        campo.addEventListener("blur", function () {
            if (campo.value.trim() !== "") {
                validarCampo(formulario, nombreCampo);
            }
        });
        campo.addEventListener("input", function () {
            if (campo.closest(".campo").classList.contains("con-error")) {
                validarCampo(formulario, nombreCampo);
            }
        });
    });

    formulario.addEventListener("submit", function (evento) {
        evento.preventDefault();

        let primerCampoConError = null;
        campos.forEach(function (nombreCampo) {
            const valido = validarCampo(formulario, nombreCampo);
            if (!valido && primerCampoConError === null) {
                primerCampoConError = formulario.elements[nombreCampo];
            }
        });

        if (primerCampoConError !== null) {
            primerCampoConError.focus();
            return;
        }
        // Pendiente (decisión abierta): enviar la preinscripción al backend.
        mostrarConfirmacion(formulario);
    });

    // Botón "Volver": usa el historial del navegador (objeto history)
    document.getElementById("boton-volver").addEventListener("click", function () {
        if (history.length > 1) {
            history.back();
        } else {
            location.href = "index.html";
        }
    });
}

// ---------------------------------------------------------------------
// Arranque: cada página inicializa lo suyo
// ---------------------------------------------------------------------

document.addEventListener("DOMContentLoaded", function () {
    const pagina = document.body.id;
    if (pagina === "pagina-inicio") {
        iniciarPaginaInicio();
    } else if (pagina === "pagina-actividades") {
        iniciarPaginaActividades();
    } else if (pagina === "pagina-preinscripcion") {
        iniciarPaginaPreinscripcion();
    }
});
