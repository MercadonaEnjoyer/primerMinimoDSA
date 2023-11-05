package edu.upc.dsa.classes;

public class juegosUsuario {
    String Usuario;
    String Juego;
    int punt;
    String fecha;

    public juegosUsuario(){}
    public juegosUsuario(String usuario, String juego, int punt, String fecha) {
        this.setUsuario(usuario);
        this.setJuego(juego);
        this.setPunt(punt);
        this.setFecha(fecha);
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getJuego() {
        return Juego;
    }

    public void setJuego(String juego) {
        Juego = juego;
    }

    public int getPunt() {
        return punt;
    }

    public void setPunt(int punt) {
        this.punt = punt;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
