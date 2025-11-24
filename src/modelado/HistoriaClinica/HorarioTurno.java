package modelado.HistoriaClinica;

public enum HorarioTurno {
    H_09_00,
    H_10_00,
    H_11_00,
    H_14_00,
    H_15_00,
    H_16_00;

    @Override
    public String toString() {
        return this.name().replace("H_", "").replace("_", ":");
    }
}