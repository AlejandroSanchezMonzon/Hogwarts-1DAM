package es.dam.repaso05.dto;

import es.dam.repaso05.models.Mago;

import java.io.Serializable;
import java.time.LocalDate;

public class MagoDTO implements Serializable {
    private int id;
    private String nombre;
    private String apodo;
    private LocalDate fNacimiento;
    private String casa;
    private int altura;
    private String hechizo;

    public MagoDTO(Mago mago) {
        this.id = mago.getId();
        this.nombre = mago.getNombre();
        this.apodo = mago.getApodo();
        this.fNacimiento = mago.getfNacimiento();
        this.casa = mago.getCasa();
        this.altura = mago.getAltura();
        this.hechizo = mago.getHechizo();
    }

    public MagoDTO(int id, String nombre, String apodo, LocalDate fNacimiento, String casa, int altura, String hechizo) {
        this.id = id;
        this.nombre = nombre;
        this.apodo = apodo;
        this.fNacimiento = fNacimiento;
        this.casa = casa;
        this.altura = altura;
        this.hechizo = hechizo;
    }

    public Mago fromDTO() {
        return new Mago(id, nombre, apodo, fNacimiento, casa, altura, hechizo);
    }

    public String toFile() {
        return id + ',' + nombre + ',' + apodo + ',' + fNacimiento + ',' + casa + ',' + altura + ',' + hechizo + "\n";
    }
}
