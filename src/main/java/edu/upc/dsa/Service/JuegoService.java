package edu.upc.dsa.Service;

import edu.upc.dsa.Exceptions.juegoNoExisteException;
import edu.upc.dsa.Exceptions.usuarioNoExisteException;
import edu.upc.dsa.classes.*;
import edu.upc.dsa.JuegoManager;
import edu.upc.dsa.JuegoManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Api(value = "/example", description = "Endpoint to example service")
@Path("/example")
public class JuegoService {

    private JuegoManager jm;

    public JuegoService() throws usuarioNoExisteException {
        this.jm = JuegoManagerImpl.getInstance();
        if(jm.sizeUsers() == 0){
            this.jm.addUser("Mario", "1234");
            this.jm.addUser("Pau", "1234");
            this.jm.addUser("Zipi","1234");
        }

    }

    @POST
    @ApiOperation(value = "crear un juego", notes = "so no head?")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 500, message = "Juego ya existe o faltan datos")
    })
    @Path("/addJuego/{id}/{Descripcion}/{nNiveles}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(@PathParam("id") String id,@PathParam("Descripcion") String desc,@PathParam("nNiveles") int n) throws usuarioNoExisteException {
        if (id==null || desc==null || n==0)  return Response.status(500).build();
        int ok = jm.addJuego(id, desc, n);
        if (ok == -1)
            return Response.status(500).build();
        return Response.status(201).build();
    }

    @POST
    @ApiOperation(value = "Crear partida", notes = "Introducir jugador y juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 500, message = "Error, usuario o juegos incorrectos o el usuario ya esta en partida")
    })
    @Path("/iniciarPartida/{Usuario}/{Juego}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComanda(@PathParam("Usuario") String uId, @PathParam("Juego") String jId) throws usuarioNoExisteException, juegoNoExisteException {
        Usuario u = jm.getUser(uId);
        Juego j = jm.getJuego(jId);
        if (u == null || j == null || u.getJuego() != null)  return Response.status(500).build();
        jm.iniciarPartida(jId, uId);
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "Nivel actual", notes = "Consulta el nivel actual de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = String.class),
            @ApiResponse(code = 500, message = "Error, usuario no existe o no esta en partida")
    })
    @Path("/nivel/{Nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  getNivel(@PathParam("Nombre") String uId) throws usuarioNoExisteException {
        Usuario u = jm.getUser(uId);
        if(u != null){
            String r = jm.nivelUsuario(uId);
            return Response.status(201).entity(r).build();
        }
        return Response.status(500).build();
    }

    @GET
    @ApiOperation(value = "Puntuacion actual", notes = "Consulta la puntuacion actual de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = String.class),
            @ApiResponse(code = 500, message = "Error, usuario no existe o no esta en partida")
    })
    @Path("/puntuacion/{Nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  getPuntuacion(@PathParam("Nombre") String uId) throws usuarioNoExisteException {
        Usuario u = jm.getUser(uId);
        if(u != null){
            String r = ("Puntuacion del usuario: " + jm.puntuacionUsuario(uId));
            return Response.status(201).entity(r).build();
        }
        return Response.status(500).build();
    }

    @PUT
    @ApiOperation(value = "Pasar nivel", notes = "Fecha con formato dd-mm-yyyy")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 500, message = "Jugador no ha iniciado partida o incorrecto")
    })
    @Path("/pasarNivel/{Nombre}/{Puntos}/{Fecha}")
    public Response pasarNivel(@PathParam("Nombre") String uId, @PathParam("Puntos") int p,@PathParam("Fecha") String date) throws usuarioNoExisteException {
        Usuario u = jm.getUser(uId);
        if (u != null || u.getJuego() != null){
            jm.pasarNivel(uId, p, date);
            return Response.status(201).build();
        }
        return Response.status(500).build();
    }
    @PUT
    @ApiOperation(value = "Finalizar partida", notes = "Fecha con formato dd-mm-yyyy")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 500, message = "Jugador no en partida o erroneo")
    })
    @Path("/finPartida/{Nombre}/{Fecha}")
    public Response finalizarPartida(@PathParam("Nombre") String uId,@PathParam("Fecha") String date) throws usuarioNoExisteException {
        Usuario u = jm.getUser(uId);
        if (u != null || u.getJuego() != null){
            jm.finPartida(uId, date);
            return Response.status(201).build();
        }
        return Response.status(500).build();
    }
    @GET
    @ApiOperation(value = "Jugadores de un juego por puntuacion", notes = "Listado jugadores ordenados de forma ascendente por puntuacion")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = juegosUsuario.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Juego no existe")
    })
    @Path("/participantesJuego/{Juego}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  getAllProducts(@PathParam("Juego") String jId) throws juegoNoExisteException {
        if (jm.getJuego(jId) == null)
            return Response.status(500).build()  ;
        List<juegosUsuario> jUsuario = this.jm.sortList(jId);
        GenericEntity<List<juegosUsuario>> entity = new GenericEntity<List<juegosUsuario>>(jUsuario) {};
        return Response.status(201).entity(entity).build();
    }
    @GET
    @ApiOperation(value = "Partidas de un jugador", notes = "Listado de partidas de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = juegosUsuario.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Usuario no existe")

    })
    @Path("/juegosJugados/{Usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  partidasJugador(@PathParam("Usuario") String uId) throws juegoNoExisteException, usuarioNoExisteException {
        if (jm.getUser(uId) == null)
            return Response.status(500).build()  ;
        List<juegosUsuario> jUsuario = this.jm.juegosParticipados(uId);
        GenericEntity<List<juegosUsuario>> entity = new GenericEntity<List<juegosUsuario>>(jUsuario) {};
        return Response.status(201).entity(entity).build();
    }
}
