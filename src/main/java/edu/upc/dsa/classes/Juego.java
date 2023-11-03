package edu.upc.dsa.classes;

import java.util.LinkedList;
import java.util.List;

public class Juego {

    String id;
    String descripcion;
    int niveles;
    LinkedList<juegosUsuario> lUsuarios;

    public Juego(){}

    public Juego(String id, String descripcion, int niveles) {
        this.setId(id);
        this.setDescripcion(descripcion);
        this.setNiveles(niveles);
        this.setlUsuarios();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNiveles() {
        return niveles;
    }

    public void setNiveles(int niveles) {
        this.niveles = niveles;
    }

    public List<juegosUsuario> getlUsuarios() {
        return lUsuarios;
    }

    public void setlUsuarios() {
        this.lUsuarios = new LinkedList<>();
    }

    public void addUsuario(juegosUsuario u){
        this.lUsuarios.add(u);
    }
}
