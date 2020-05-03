package sample;

import javafx.beans.property.SimpleStringProperty;

public class materiasAinscribir {
    public SimpleStringProperty id;
    public SimpleStringProperty materia;
    public SimpleStringProperty dia;
    public SimpleStringProperty horarios;

    materiasAinscribir(String id, String materia, String dia, String horarios) {
        this.id = new SimpleStringProperty(id.toString());
        this.materia = new SimpleStringProperty(materia);
        this.dia = new SimpleStringProperty(dia);
        this.horarios = new SimpleStringProperty(horarios);
    }

    private String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    private void setId(String id) {
        this.id.set(id);
    }

    private String getMateria() {
        return materia.get();
    }

    public SimpleStringProperty materiaProperty() {
        return materia;
    }

    private void setMateria(String materia) {
        this.materia.set(materia);
    }

    private String getDia() {
        return dia.get();
    }

    public SimpleStringProperty diaProperty() {
        return dia;
    }

    private void setDia(String dia) {
        this.dia.set(dia);
    }

    private String getHorarios() {
        return horarios.get();
    }

    public SimpleStringProperty horariosProperty() {
        return horarios;
    }

    private void setHorarios(String horarios) {
        this.horarios.set(horarios);
    }
}
