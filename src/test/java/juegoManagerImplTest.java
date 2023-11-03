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
import java.util.logging.Logger;

public class juegoManagerImplTest {

    final static Logger logger = Logger.getLogger(String.valueOf(JuegoManagerImpl.class));
    JuegoManager jm;

    @Before
    public void setUp() throws usuarioNoExisteException {
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
    }

    @After
    public void tearDown(){
        logger.info("--------Final Tests--------");
    }

    @Test
    public void crearPartida() throws usuarioNoExisteException, juegoNoExisteException {
        logger.info("--------Crear Partida--------");
        jm.iniciarPartida("Ligoleyen","Mario");
        Assert.assertEquals("Ligoleyen", jm.getUser("Mario").getJuego().getId());
    }

    @Test
    public void testPartida() throws usuarioNoExisteException, juegoNoExisteException {
        logger.info("--------Comprobando Partida--------");
        jm.iniciarPartida("Ligoleyen","Mario");
        Assert.assertEquals("Ligoleyen", jm.getUser("Mario").getJuego().getId());
        jm.nivelUsuario("Mario");
        jm.pasarNivel("Mario", 250, "12-10-22");
        Assert.assertEquals(2, jm.getUser("Mario").getNiv());
        Assert.assertEquals(300, jm.getUser("Mario").getPuntos());
        jm.pasarNivel("Mario", 250, "12-10-22");
        jm.pasarNivel("Mario", 250, "12-10-22");
    }

    @Test
    public void testFinPartida() throws usuarioNoExisteException, juegoNoExisteException {
        logger.info("--------Comprobando Partida--------");
        jm.iniciarPartida("Lies of P","Mario");
        Assert.assertEquals("Lies of P", jm.getUser("Mario").getJuego().getId());
        jm.nivelUsuario("Mario");
        jm.pasarNivel("Mario", 250, "12-10-22");
        jm.finPartida("Mario", "12-10-22");
        Assert.assertNull(jm.getUser("Mario").getJuego());
    }
    @Test
    public void testPuntuaciones() throws usuarioNoExisteException, juegoNoExisteException {
        logger.info("--------Comprobando Partida--------");
        jm.iniciarPartida("Ligoleyen","Mario");
        jm.iniciarPartida("Ligoleyen", "Pau");
        jm.iniciarPartida("Ligoleyen", "Zipi");
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

}
