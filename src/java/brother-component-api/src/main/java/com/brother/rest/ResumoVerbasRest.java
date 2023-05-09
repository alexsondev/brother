package com.brother.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Context;

import com.totvs.technology.foundation.common.EncodedMediaType;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.component.*;

/**
 * API chamada pelo portal do cliente para listar as verbas do usuário
 * 
 * Endpoint
 * 
 * POST: /brother-api/v1/resumo | Lista resumo de verbas do usuário
 * 
 */

@Path("/resumo")
public class ResumoVerbasRest {

    private Logger log = LoggerFactory.getLogger(ResumoVerbasRest.class);

    @GET
    @Path("/search/{guid}")
    @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
    @Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
    public Response getResumoVerbas(@PathParam("guid") String guid,
            @Context HttpHeaders headers) {
        try {

            log.info(String.format("---- Brother - API Request | GET: /resumo/search v3"));
            log.info(String.format("---- Brother - API Request | GET: /resumo/search guid: %s",
                    guid));

            ResumoVerbasVO[] resumoVerbas = ResumoVerbas.getResumoVerbas(guid);

            return Response.status(Response.Status.OK).entity(resumoVerbas).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorStatus(e))
                    .build();
        }
    }

}
