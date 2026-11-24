import { calcularCuotaMensual, porcentajeDescuento } from "./calcularCuotaMensual";

// Cuotas reales del club: Yoga $17.000, Natación $26.000, Funcional $22.000
const yoga = { nombre: "Yoga", cuotaMensual: 17000 };
const natacion = { nombre: "Natación", cuotaMensual: 26000 };
const funcional = { nombre: "Funcional adultos", cuotaMensual: 22000 };

describe("calcularCuotaMensual (RN6)", () => {
  test("sin actividades la cuota es 0", () => {
    expect(calcularCuotaMensual([])).toBe(0);
  });

  test("con 1 actividad paga la cuota completa, sin descuento", () => {
    expect(calcularCuotaMensual([yoga])).toBe(17000);
  });

  test("con 2 actividades tiene 10 % de descuento", () => {
    // (17.000 + 26.000) * 0,90 = 38.700
    expect(calcularCuotaMensual([yoga, natacion])).toBe(38700);
  });

  test("con 3 actividades tiene 15 % de descuento", () => {
    // (17.000 + 26.000 + 22.000) * 0,85 = 55.250
    expect(calcularCuotaMensual([yoga, natacion, funcional])).toBe(55250);
  });

  test("acepta cuotas que llegan de la API como texto o con decimales", () => {
    expect(calcularCuotaMensual([{ cuotaMensual: "17000.00" }])).toBe(17000);
  });

  test("si no recibe un arreglo devuelve 0", () => {
    expect(calcularCuotaMensual(undefined)).toBe(0);
  });
});

describe("porcentajeDescuento", () => {
  test("0 y 1 actividad no tienen descuento; 2 tienen 10 % y 3 tienen 15 %", () => {
    expect(porcentajeDescuento(0)).toBe(0);
    expect(porcentajeDescuento(1)).toBe(0);
    expect(porcentajeDescuento(2)).toBe(10);
    expect(porcentajeDescuento(3)).toBe(15);
  });
});
