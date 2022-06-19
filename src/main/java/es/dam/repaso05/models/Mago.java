package es.dam.repaso05.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Mago {
    private int id;
    private final StringProperty nombre;
    private final StringProperty apodo;
    private final ObjectProperty<LocalDate> fNacimiento;
    private final StringProperty casa;
    private final IntegerProperty altura;
    private final StringProperty hechizo;

    public Mago(int id, String nombre, String apodo, LocalDate fNacimiento, String casa, int altura, String hechizo) {
        this.id = id;
        this.nombre = new SimpleStringProperty(nombre);
        this.apodo = new SimpleStringProperty(apodo);
        this.fNacimiento = new SimpleObjectProperty<LocalDate>(fNacimiento);
        this.casa = new SimpleStringProperty(casa);
        this.altura = new SimpleIntegerProperty(altura);
        this.hechizo = new SimpleStringProperty(hechizo);
    }

    public Mago(String nombre, String apodo, LocalDate fNacimiento, String casa, int altura, String hechizo) {
        this.nombre = new SimpleStringProperty(nombre);
        this.apodo = new SimpleStringProperty(apodo);
        this.fNacimiento = new SimpleObjectProperty<LocalDate>(fNacimiento);
        this.casa = new SimpleStringProperty(casa);
        this.altura = new SimpleIntegerProperty(altura);
        this.hechizo = new SimpleStringProperty(hechizo);
    }

    public Mago() {
        this(0, null, null, null, null, 0, null);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getApodo() {
        return apodo.get();
    }

    public StringProperty apodoProperty() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo.set(apodo);
    }

    public LocalDate getfNacimiento() {
        return fNacimiento.get();
    }

    public ObjectProperty<LocalDate> fNacimientoProperty() {
        return fNacimiento;
    }

    public void setfNacimiento(LocalDate fNacimiento) {
        this.fNacimiento.set(fNacimiento);
    }

    public String getCasa() {
        return casa.get();
    }

    public StringProperty casaProperty() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa.set(casa);
    }

    public int getAltura() {
        return altura.get();
    }

    public IntegerProperty alturaProperty() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura.set(altura);
    }

    public String getHechizo() {
        return hechizo.get();
    }

    public StringProperty hechizoProperty() {
        return hechizo;
    }

    public void setHechizo(String hechizo) {
        this.hechizo.set(hechizo);
    }

    @Override
    public String toString() {
        return "Mago{" +
                "id=" + id +
                ", nombre=" + nombre +
                ", apodo=" + apodo +
                ", fNacimiento=" + fNacimiento +
                ", casa=" + casa +
                ", altura=" + altura +
                ", hechizo=" + hechizo +
                '}';
    }
}
