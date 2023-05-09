package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class MarketingTextosPortalComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingTextosPortalComponent.class);

  public static MarketingTextosPortalVO getMarketingTextosPortal() throws Exception {
    try {

      log.info(String.format(
          "=*=*=*=*= brother-component | MarketingTextosPortalComponent | getMarketingTextosPortal"));

      String path = "/api/public/ecm/dataset/search?datasetId=fluig_busca_textos_portal";

      JSONObject MarketingTextosPortalObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingTextosPortalObject.getJSONArray("content");

      MarketingTextosPortalVO marketingTextosPortalVO = new MarketingTextosPortalVO();


      String portalDadosTexto = contentArray.getJSONObject(0).optString("portalDadosTexto");
      String portalEvidenciasTexto =
          contentArray.getJSONObject(0).optString("portalEvidenciasTexto");
      String portalNDTexto = contentArray.getJSONObject(0).optString("portalNDTexto");
      String portalPagamentoTexto = contentArray.getJSONObject(0).optString("portalPagamentoTexto");

      marketingTextosPortalVO = new MarketingTextosPortalVO(portalDadosTexto, portalEvidenciasTexto,
          portalNDTexto, portalPagamentoTexto);

      return marketingTextosPortalVO;

    } catch (Exception e) {
      throw e;
    }
  }
}
