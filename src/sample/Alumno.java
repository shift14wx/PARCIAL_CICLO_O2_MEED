package sample;

import javafx.beans.property.SimpleStringProperty;

public class Alumno {

    public String name;
    private SimpleStringProperty id;

    Alumno(String name, String id) {
        this.name = name;
        this.id = new SimpleStringProperty(id);
    }

    private String getName() {
        return name;
    }

    private String getId() {
        return id.toString();
    }
}
