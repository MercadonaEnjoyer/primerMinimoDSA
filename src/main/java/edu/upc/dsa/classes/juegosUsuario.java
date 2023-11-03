package edu.upc.dsa.classes;

public class juegosUsuario {
    String Usuario;
    int punt;
    String fecha;

    public juegosUsuario(String usuario, int punt, String fecha) {
        this.setUsuario(usuario);
        this.setPunt(punt);
        this.setFecha(fecha);
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
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
