package modelado.HistoriaClinica;

public enum TipoTratamiento {
    RADIOGRAFIA("Radiografía"),
    ANALISIS_SANGRE("Análisis de sangre"),
    ECOGRAFIA("Ecografía"),
    VACUNA_ANTIRRABICA("Vacuna antirrabica"),
    DESPARASITACION("Desparasitación interna"),
    SUTURA("Sutura de herida");

    private final String descripcion;

    // CONSTRUCTOR
    TipoTratamiento(String descripcion) {
        this.descripcion = descripcion;
    }

    // getters y setters
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
