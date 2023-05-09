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
 * API chamada pelo portal do cliente para movimentar o fluxo do Marketing
 * 
 * Endpoint
 * 
 * POST: /brother-api/v1/marketing | Movimenta o fluxo do marketing conforme uuid recebido
 * 
 */

@Path("/marketing")
public class MarketingComponentRest {

  private Logger log = LoggerFactory.getLogger(MarketingComponentRest.class);

  @GET
  @Path("/search/{guid}")
  @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
  @Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
  public Response getMarketingFlowData(@PathParam("guid") String guid,
      @Context HttpHeaders headers) {
    try {

      log.info(String.format("---- Brother - API Request | GET: /marketing/search v3"));
      log.info(String.format("---- Brother - API Request | GET: /marketing/search guid: %s", guid));

      MarketingFlowVO marketingFlow = MarketingFlowComponent.getMarketingData(guid);

      return Response.status(Response.Status.OK).entity(marketingFlow).build();

    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorStatus(e))
          .build();
    }
  }

  @POST
  @Path("/update")
  @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
  @Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
  public Response updateMarketingData(MarketingFlowVO solicitacao, @Context HttpHeaders headers)
      throws Exception {
    try {

      String guid = headers.getHeaderString("guid");
      Boolean completeTask = headers.getHeaderString("completeTask") == "true";

      log.info(String.format("---- POST: /marketing/update guid: %s", guid));

      if (guid == "") {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }

      MarketingFlowVO solicitacaoResponse =
          MarketingFlowComponent.updateMarketingData(guid, solicitacao, completeTask);

      return Response.status(Response.Status.OK).entity(solicitacaoResponse).build();

    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorStatus(e))
          .build();
    }
  }

  @GET
  @Path("/workflowVo/{guid}")
  @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
  @Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
  public Response getUpdateTest(@PathParam("guid") String guid, @Context HttpHeaders headers)
      throws Exception {
    try {

      MarketingFlowVO solicitacao = MarketingFlowComponent.getMarketingData(guid);

      ECMWorkflowVO workflowVO = MarketingFlowComponent.getWorkflowVO(guid, solicitacao, false);

      return Response.status(Response.Status.OK).entity(workflowVO).build();

    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorStatus(e))
          .build();
    }
  }

  @GET
  @Path("/textos")
  @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
  @Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
  public Response getMarketingFlowData(@Context HttpHeaders headers) {
    try {

      log.info(String.format("---- Brother - API Request | GET: /marketing/textos v3"));

      MarketingTextosPortalVO marketingTextosPortalFlow =
          MarketingTextosPortalComponent.getMarketingTextosPortal();

      return Response.status(Response.Status.OK).entity(marketingTextosPortalFlow).build();

    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorStatus(e))
          .build();
    }
  }

}
