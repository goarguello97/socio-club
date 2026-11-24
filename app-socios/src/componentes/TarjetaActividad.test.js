import { render, screen, fireEvent } from "@testing-library/react";
import TarjetaActividad from "./TarjetaActividad";

const yoga = {
  id: 8,
  nombre: "Yoga",
  dias: "Mar y Jue",
  horario: "09:00",
  edadMinima: 16,
  edadMaxima: 99,
  profesor: "Inés Arguello",
  cupoMaximo: 15,
  cupoDisponible: 1,
  cuotaMensual: 17000,
};

describe("TarjetaActividad", () => {
  test("muestra el nombre de la actividad", () => {
    render(<TarjetaActividad actividad={yoga} onElegir={() => {}} />);

    expect(screen.getByRole("heading", { name: "Yoga" })).toBeInTheDocument();
  });

  test("con cupo 0 muestra 'Sin cupo' y el botón queda deshabilitado", () => {
    render(<TarjetaActividad actividad={{ ...yoga, cupoDisponible: 0 }} onElegir={() => {}} />);

    expect(screen.getByText("Sin cupo")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Inscribirme" })).toBeDisabled();
  });

  test("con lugar, el botón está habilitado y avisa qué actividad se eligió", () => {
    const onElegir = jest.fn();
    render(<TarjetaActividad actividad={yoga} onElegir={onElegir} />);

    expect(screen.queryByText("Sin cupo")).not.toBeInTheDocument();
    fireEvent.click(screen.getByRole("button", { name: "Inscribirme" }));
    expect(onElegir).toHaveBeenCalledWith(8);
  });

  test("avisa cuando la actividad no corresponde a la edad del socio", () => {
    render(<TarjetaActividad actividad={yoga} onElegir={() => {}} esParaMiEdad={false} />);

    expect(screen.getByText("No corresponde a tu edad")).toBeInTheDocument();
  });
});
