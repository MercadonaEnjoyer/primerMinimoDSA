package edu.upc.dsa;

import edu.upc.dsa.Exceptions.juegoNoExisteException;
import edu.upc.dsa.Exceptions.usuarioNoExisteException;
import edu.upc.dsa.classes.*;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class JuegoManagerImpl implements JuegoManager{

    private static JuegoManager instance;
    protected Map<String, Usuario> lUsuarios;
    protected Map<String, Juego> lJuegos;

    final static Logger logger = Logger.getLogger(JuegoManagerImpl.class);

    public JuegoManagerImpl() {
        this.lUsuarios = new HashMap<>();
        this.lJuegos = new HashMap<>();
    }

    public static JuegoManager getInstance() {
        if (instance==null) instance = new JuegoManagerImpl();
        return instance;
    }

    //--------------------------------------Partida------------------------------------------//

    public int addJuego(String id, String desc, int nNiv){
        try{
            getJuego(id);
        }catch (juegoNoExisteException e){
            Juego j = new Juego(id, desc, nNiv);
            lJuegos.put(id, j);
            logger.info("Juego creado: " + id);
            return 0;
        }
        logger.info("Juego ya existe");
        return -1;
    }

    public Juego getJuego(String id) throws juegoNoExisteException{
        if(lJuegos.get(id) == null){
            throw new juegoNoExisteException();
        }
        return lJuegos.get(id);
    }

    public void iniciarPartida(String jId, String uId) throws usuarioNoExisteException, juegoNoExisteException {
        try {
            Juego j = getJuego(jId);
            Usuario u = getUser(uId);
            if (u.getJuego() != null){
                logger.info("Usuario ya en una partida");
                return;
            }
            logger.info(uId + " iniciando el juego: " + jId);
            u.setJuego(j);
            u.setPuntos(50);
            u.setNiv(1);
        }catch (usuarioNoExisteException e){
            logger.info("Usuario no existe!");
        }catch (juegoNoExisteException e){
            logger.info("Juego no existe");
        }
    }

    public List<juegosUsuario> sortList(String jId) throws juegoNoExisteException {
        try {
            List<juegosUsuario> lJu = getJuego(jId).getlUsuarios();
            lJu.sort((juegosUsuario o1, juegosUsuario o2) -> Integer.compare(o2.getPunt(),(o1.getPunt())));
            for(juegosUsuario ju : lJu){
                logger.info("Jugador: " + ju.getUsuario() + " - Puntuacion: " + ju.getPunt());
            }
            return lJu;
        }catch (juegoNoExisteException e){
            logger.info("Juego no existe");
            return null;
        }
    }

    //--------------------------------------Usuarios------------------------------------------//

    public Usuario addUser(String name, String contra) {
        logger.info("Creando nuevo usuario: " + name);
        try{
            getUser(name);
            logger.info("Usuario ya en uso!");
        }catch (usuarioNoExisteException e){
            Usuario u = new Usuario(name, contra);
            lUsuarios.put(name, u);
            logger.info("Usuario creado con exito!");
            return u;
        }
        return null;
    }

    public Usuario getUser(String name) throws usuarioNoExisteException{
        if(lUsuarios.get(name) == null){
            throw new usuarioNoExisteException();
        }
        return lUsuarios.get(name);
    }

    public String nivelUsuario(String uId) throws usuarioNoExisteException {
        try{
            Usuario u = getUser(uId);
            return ("El usuario " + uId + " se encuentra en el nivel " + u.getNiv() + " del juego " + u.getJuego().getId());
        }catch (usuarioNoExisteException e){
            logger.info("Usuario no existe");
        }
        return null;
    }

    public int puntuacionUsuario(String uId) throws usuarioNoExisteException {
        try{
            Usuario u = getUser(uId);
            logger.info("Puntuacion usuario: " + u.getPuntos());
            return u.getPuntos();
        }catch (usuarioNoExisteException e){
            logger.info("Usuario no existe");
        }
        return -1;
    }

    public int pasarNivel(String uId, int punt, String fecha) throws usuarioNoExisteException {
        try{
            Usuario u = getUser(uId);
            if(u.getNiv() == u.getJuego().getNiveles()){
                u.setPuntos(u.getPuntos() + 100);
                finPartida(uId, fecha);
            }
            u.setNiv(u.getNiv() + 1);
            u.setPuntos(u.getPuntos() + punt);
            logger.info("Accediendo al siguiente nivel...");
        }catch (usuarioNoExisteException e){
            logger.info("Usuario no existe");
        }
        return -1;
    }

    public int finPartida(String uId, String fecha){
        try{
            Usuario u = getUser(uId);
            if(u.getJuego() != null){
                juegosUsuario ju = new juegosUsuario(uId, u.getJuego().getId(), u.getPuntos(), fecha);
                u.getJuego().addUsuario(ju);
                u.addJParticipados(ju);
                u.setJuego(null);
                logger.info("Fin del juego");
                return 0;
            }
            logger.info("Usuario no en partida");
            return -1;
        }catch (usuarioNoExisteException e){
            logger.info("Usuario no existe");
        }
        return -1;
    }

    public List<juegosUsuario> juegosParticipados(String uId) throws usuarioNoExisteException {
        Usuario u = getUser(uId);
        logger.info("Generndo lista de juegos participados");
        if (u != null){
            return u.getjParticipados();
        }
        return null;
    }

    public int sizeUsers(){
        logger.info("Numero de usuarios en la lista: " + this.lUsuarios.size());
        return this.lUsuarios.size();
    }


}
