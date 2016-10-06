/*
 * PROGRAMA PROPIEDAD DE LA EMPRESA DE ENERGIA DE ARAUCA ENELAR E.S.P.
 * Este codigo fuente es de uso exclusivo de la empresa de energia de arauca
 */
package com.enelar.pr100_pr001;

import com.enelar.libreria_enelar.LoginModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.view.Viewable;
import java.sql.SQLException;
import java.util.Map;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ENELAR E.S.P.
 */
@Stateless
@Path("")
public class pr100_pr001Controller {

    LoginModel user;
    pr100_pr001Model model;
    Gson gson;

    public pr100_pr001Controller() {
        user = new LoginModel();
        model = new pr100_pr001Model();
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    @GET
    @Path("{tipo}")
    @Produces(MediaType.TEXT_HTML)
    public Response Vistas(
            @PathParam("tipo") String tipo,
            @Context HttpServletRequest request
    ) {
        if (user.VerificaSesion(request)) {
            Map<String, Object> resultado;
            resultado = user.DatosUsuario(request.getSession().getId());
            if (resultado.containsKey("mensaje") && !resultado.get("mensaje").equals("OK")) {
                return Response.ok().entity(gson.toJson(user.AccesoDenegado())).header("content-type", MediaType.APPLICATION_JSON).build();
            } else {
                user.DatosSesion(request, resultado);
            }
        }
        switch (tipo) {
            case "principal":
                return Response.ok(new Viewable("/principal")).build();
            default:
                return Response.status(204).build();
        }
    }

    @POST
    @Path("datos/{tipo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Suscriptores(
            @PathParam("tipo") String tipo,
            @FormParam("json") String json,
            @Context HttpServletRequest request) {
        if (user.VerificaSesion(request)) {
            Map<String, Object> resultado;
            resultado = user.DatosUsuario(request.getSession().getId());
            if (resultado.containsKey("mensaje") && !resultado.get("mensaje").equals("OK")) {
                return Response.ok().entity(gson.toJson(user.AccesoDenegado())).header("content-type", MediaType.APPLICATION_JSON).build();
            } else {
                user.DatosSesion(request, resultado);
            }
        }
        //String sid = "SIC";
        String sid = request.getSession().getAttribute("sid").toString();
		String url = "https://" + request.getServerName() + ":" + request.getServerPort();
        switch (tipo) {
            case "listar":
                return Response.ok().entity(
                        gson.toJson(model.DatosConvenio(json))
				).build();
            default:
                return Response.status(204).build();
        }
    }
}
