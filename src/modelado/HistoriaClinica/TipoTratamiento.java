package modelado.HistoriaClinica;

public enum TipoTratamiento {
    RADIOGRAFIA("Radiografía"),
    ANALISIS_SANGRE("Análisis de sangre"),
    ECOGRAFIA("Ecografía"),
    VACUNA_ANTIRRABICA("Vacuna antirrabica"),
    VACUNA_QUINTUPLE("Vacuna quintuple"),
    DESPARASITACION("Desparasitación interna"),
    SUTURA("Sutura de herida");

    private final String descripcion;

    // CONSTRUCTOR
    TipoTratamiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
