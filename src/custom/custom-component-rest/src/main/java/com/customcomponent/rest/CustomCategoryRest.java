package com.customcomponent.rest;

import com.fluig.sdk.api.FluigAPI;
import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.UserService;
import com.customcomponent.entity.CustomCategory;
import com.customcomponent.service.CustomCategoryService;
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
 * GET:    /customcomponent/v1/category      | Solicita uma informação(lista) que está no fluig 
 * GET:    /customcomponent/v1/category/{id} | Solicita uma informação(item único) que está no fluig
 * POST:   /customcomponent/v1/category      | Persiste uma informação no fluig
 * PUT:    /customcomponent/v1/category      | Atualiza uma informação no fluig
 * DELETE: /customcomponent/v1/category      | Remove uma informação no fluig
 *
 *  onde:
 *  /customcomponent é o contexto que foi registrado através do arquivo jboss-web.xml no projeto custom-component-config
 *  /v1 é o ApplicationPath, que está na classe ApplicationConfig
 *  /category, que é o path registrado para essa classe em específico
 *
 */
@Path("/category")
public class CustomCategoryRest {
	private Logger log = LoggerFactory.getLogger(CustomCategoryRest.class);

	@GET
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response find(
			@DefaultValue("") @QueryParam("text") String text,
			@DefaultValue("10") @QueryParam("limit") int limit,
			@DefaultValue("0") @QueryParam("offset") int offset) throws Exception {
		log.info("---- Category Request | GET find ");
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		return Response.ok(categoryService().find(text, limit>50?50:limit, offset)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response get(@PathParam("id") Long id) throws Exception {
		log.info("---- Category Request | GET getById");
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());		
		CustomCategory category = categoryService().get(id);
		if(category == null)
			return Response.status(Status.NOT_FOUND).entity("No Category found for ID: " + id).build();
		return Response.ok(category).build();
	}

	@POST
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(CustomCategory vo) throws Exception {
		log.info("---- Category Request | POST");
		log.info("---- Object to create: " + vo.toString());
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		try {
			return Response.status(Response.Status.CREATED).entity(categoryService().create(vo)).build();
		} catch (FDNCreateException e) {
		    return Response.status(e.getStatus()).entity(e.getMessage()).build();
		}
	}

	@PUT
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response update(CustomCategory vo) throws Exception {
		log.info("---- Category Request | PUT");
		log.info("---- Object to update: " + vo.toString());
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());		
		try {
			categoryService().update(vo);
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
		log.info("---- Category Request | DELETE");
		log.info("---- Object to delete: " + id);
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());		
		try {
			categoryService().delete(id);
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

	private CustomCategoryService categoryService() throws NamingException {
		return (CustomCategoryService) ServiceLocator.getInstance().getService(CustomCategoryService.JNDI_NAME);
	}
}
