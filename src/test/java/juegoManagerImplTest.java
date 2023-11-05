import edu.upc.dsa.Exceptions.juegoNoExisteException;
import edu.upc.dsa.Exceptions.usuarioNoExisteException;
import edu.upc.dsa.classes.*;
import edu.upc.dsa.JuegoManagerImpl;
import edu.upc.dsa.JuegoManager;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class juegoManagerImplTest {

    final static Logger logger = Logger.getLogger(String.valueOf(JuegoManagerImpl.class));
    JuegoManager jm;

    @Before
    public void setUp() throws usuarioNoExisteException, juegoNoExisteException {
        logger.info("--------Generando datos--------");
        jm = new JuegoManagerImpl();
        jm.addUser("Mario", "1234");
        jm.addUser("Pau", "1234");
        jm.addUser("Zipi", "1234");
        jm.addUser("Zape", "1234");

        jm.addJuego("Ligoleyen", "Juego para toxicos sin vida como yo", 4);
        jm.addJuego("Lies of P", "Souls like del pinochet", 12);
        jm.addJuego("Dinotren el juego interactivo, la pelicula, el juego", "DINOTREN", 7);
        jm.addJuego("Cinco noches con Federico", "Juego de miedo para todas las edades", 6);

        logger.info("--------Crear Partida--------");
        jm.iniciarPartida("Ligoleyen","Mario");
        jm.iniciarPartida("Ligoleyen","Zipi");
        jm.iniciarPartida("Ligoleyen","Pau");
    }

    @After
    public void tearDown(){
        logger.info("--------Final Tests--------");
    }

    @Test
    public void testPartida() throws usuarioNoExisteException, juegoNoExisteException {
        logger.info("--------Comprobando puntuacion partida--------");
        Assert.assertEquals("Ligoleyen", jm.getUser("Mario").getJuego().getId());
        jm.nivelUsuario("Mario");
        jm.pasarNivel("Mario", 250, "12-10-22");
        Assert.assertEquals(2, jm.getUser("Mario").getNiv());
        Assert.assertEquals(300, jm.getUser("Mario").getPuntos());
        jm.pasarNivel("Mario", 250, "12-10-22");
        jm.pasarNivel("Mario", 250, "12-10-22");
        Assert.assertEquals(800, jm.getUser("Mario").getPuntos());
    }
    @Test
    public void testFinPartida() throws usuarioNoExisteException, juegoNoExisteException {
        logger.info("--------Comprobando fin partida--------");
        jm.nivelUsuario("Mario");
        jm.pasarNivel("Mario", 250, "12-10-22");
        jm.finPartida("Mario", "12-10-22");
        Assert.assertNull(jm.getUser("Mario").getJuego());
    }
    @Test
    public void testPuntuaciones() throws usuarioNoExisteException, juegoNoExisteException {
        logger.info("--------Comprobando listado puntuaciones partida--------");
        jm.pasarNivel("Mario", 250, "12-10-22");
        jm.pasarNivel("Mario", 100, "12-10-22");
        jm.finPartida("Mario", "12-10-22");
        jm.pasarNivel("Pau", 250, "12-10-22");
        jm.pasarNivel("Pau", 300, "12-10-22");
        jm.pasarNivel("Pau", 500, "12-10-22");
        jm.pasarNivel("Pau", 100, "12-10-22");
        jm.pasarNivel("Zipi", 20, "12-10-22");
        jm.finPartida("Zipi", "12-10-22");
        jm.sortList("Ligoleyen");
    }
    @Test
    public void testPartidas() throws usuarioNoExisteException, juegoNoExisteException {
        logger.info("--------Comprobando listado de juegos jugados--------");
        jm.finPartida("Mario", "12-10-22");
        jm.iniciarPartida("Dinotren el juego interactivo, la pelicula, el juego","Mario");
        jm.finPartida("Mario", "12-10-22");
        jm.iniciarPartida("Cinco noches con Federico","Mario");
        jm.finPartida("Mario", "12-10-22");
        List<juegosUsuario> lJ = jm.juegosParticipados("Mario");

        for (juegosUsuario juego : lJ){
            logger.info("Mario ha jugado a " + juego.getUsuario());
        }
    }

}
