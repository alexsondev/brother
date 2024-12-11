package com.customcomponent.rest;

import com.fluig.sdk.api.FluigAPI;
import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.UserService;
import com.customcomponent.entity.CustomApp;
import com.customcomponent.service.CustomAppService;
import com.totvs.technology.foundation.common.EncodedMediaType;
import com.totvs.technology.foundation.common.ServiceLocator;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe de exemplo para expor uma API Rest no fluig
 *
 * Existem 5 endpoint's:
 *
 * GET:    /customcomponent/v1/app      | Solicita uma informação(lista) que está no fluig
 * GET:    /customcomponent/v1/app/{id} | Solicita uma informação(item único) que está no fluig
 * POST:   /customcomponent/v1/app      | Persiste uma informação no fluig
 * PUT:    /customcomponent/v1/app      | Atualiza uma informação no fluig
 * DELETE: /customcomponent/v1/app      | Remove uma informação no fluig
 *
 *  onde:
 *  /customcomponent é o contexto que foi registrado através do arquivo jboss-web.xml no projeto custom-component-config
 *  /v1 é o ApplicationPath, que está na classe ApplicationConfig
 *  /app, que é o path registrado para essa classe em específico
 *
 */
@Path("/app")
public class CustomAppRest {
	private Logger log = LoggerFactory.getLogger(CustomAppRest.class);

	@GET
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response find(
			@DefaultValue("") @QueryParam("text") String text,
			@DefaultValue("10") @QueryParam("limit") int limit,
			@DefaultValue("0") @QueryParam("offset") int offset) throws Exception {
		log.info("---- App Request | GET find ");
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		return Response.ok(appService().find(text, limit>50?50:limit, offset)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response get(@PathParam("id") Long id) throws Exception {
		log.info("---- App Request | GET getById");
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		CustomApp app = appService().get(id);
		if(app == null)
			return Response.status(Status.NOT_FOUND).entity("No App found for ID: " + id).build();
		return Response.ok(app).build();
	}

	@POST
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(CustomApp app) throws Exception {
		log.info("---- App Request | POST");
		log.info("---- Object to create: " + app.toString());
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		try {
			return Response.status(Status.CREATED).entity(appService().create(app)).build();
		} catch (FDNCreateException e) {
		    return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
	}

	@PUT
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response update(CustomApp vo) throws Exception {
		log.info("---- App Request | PUT");
		log.info("---- Object to update: " + vo.toString());
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		try {
			appService().update(vo);
			return Response.noContent().build();
		} catch (FDNUpdateException e) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("error", e.getMessage());
			return Response.status(e.getStatus()).entity(errors).type(EncodedMediaType.APPLICATION_JSON_UTF8).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response delete(@PathParam("id") Long id) throws Exception {
		log.info("---- App Request | DELETE");
		log.info("---- Object ID Deleted: " + id);
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		try {
			appService().delete(id);
			return Response.noContent().build();
		} catch (FDNRemoveException e) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("error", e.getMessage());
			return Response.status(e.getStatus()).entity(errors).type(EncodedMediaType.APPLICATION_JSON_UTF8).build();
		}
	}

	private UserService getUserServiceSDK() throws SDKException {
		return new FluigAPI().getUserService();
	}

	private CustomAppService appService() throws NamingException {
		return (CustomAppService) ServiceLocator.getInstance().getService(CustomAppService.JNDI_NAME);
	}

}
