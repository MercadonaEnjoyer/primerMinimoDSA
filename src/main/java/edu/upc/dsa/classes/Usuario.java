package edu.upc.dsa.classes;

import edu.upc.dsa.util.idGenerator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Usuario {
    String nombre;
    String contra;
    int niv;
    int puntos;
    Juego juego;
    LinkedList<Juego> jParticipados;

    public Usuario(){}
    public Usuario(String nombre, String contra) {
        this.setContra(contra);
        this.setNombre(nombre);
        this.setjParticipados();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int getNiv() {
        return niv;
    }

    public void setNiv(int niv) {
        this.niv = niv;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public List<Juego> getjParticipados() {
        return jParticipados;
    }

    public void setjParticipados() {
        this.jParticipados = new LinkedList<Juego>();
    }

    public void addJParticipados(Juego j){
        this.jParticipados.add(j);
    }
}
