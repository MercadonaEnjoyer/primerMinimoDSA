package edu.upc.dsa;

import edu.upc.dsa.Exceptions.juegoNoExisteException;
import edu.upc.dsa.Exceptions.usuarioNoExisteException;
import edu.upc.dsa.classes.*;

import java.util.List;

public interface JuegoManager {
    public int addJuego(String id, String desc, int nNiv);
    public Juego getJuego(String id) throws juegoNoExisteException;
    public void iniciarPartida(String jId, String uId) throws usuarioNoExisteException, juegoNoExisteException;
    public List<juegosUsuario> sortList(String jId) throws juegoNoExisteException;
    public Usuario addUser(String name, String contra);
    public Usuario getUser(String name) throws usuarioNoExisteException;
    public String nivelUsuario(String uId) throws usuarioNoExisteException;
    public int puntuacionUsuario(String uId) throws usuarioNoExisteException;
    public int pasarNivel(String uId, int punt, String fecha) throws usuarioNoExisteException;
    public int finPartida(String uId, String fecha);
    public int sizeUsers();
}
