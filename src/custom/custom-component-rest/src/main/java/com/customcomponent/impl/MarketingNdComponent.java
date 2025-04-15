package com.customcomponent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customcomponent.vo.*;
import com.customcomponent.util.*;
import com.customcomponent.oauth.*;
import org.json.*;

public class MarketingNdComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingNdComponent.class);

  public static MarketingNdVO[] getMarketingNd(long solicitacaoDocumentId, Integer version) throws Exception {
    try {

      log.info(
          String.format("=*=*=*=*= brother-component | MarketingNdComponent | getMarketingNd"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,arquivosND,documentid,"
              + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingNdObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingNdObject.getJSONArray("content");

      MarketingNdVO[] marketingNdOArray = new MarketingNdVO[contentArray.length()];

      for (int i = 0; i < contentArray.length(); i++) {
        Long documentid = contentArray.getJSONObject(i).optLong("arquivoND_documentid");
        String nome = contentArray.getJSONObject(i).optString("arquivoND_nome");
        String tipo = contentArray.getJSONObject(i).optString("arquivoND_tipo");
        String ndversion = contentArray.getJSONObject(i).optString("arquivoND_version");
        String numero = contentArray.getJSONObject(i).optString("arquivoND_numero");
        String url = contentArray.getJSONObject(i).optString("arquivoND_url");
        String descricao = contentArray.getJSONObject(i).optString("arquivoND_descricao");
        Boolean aceito = contentArray.getJSONObject(i).optBoolean("arquivoND_aceito");
        String motivoRecusa = contentArray.getJSONObject(i).optString("arquivoND_motivoRecusa");
        Boolean removed = contentArray.getJSONObject(i).optBoolean("arquivoND_removed");

        marketingNdOArray[i] = new MarketingNdVO(documentid, nome, tipo, ndversion, numero, url,
            descricao, aceito, motivoRecusa, removed);
      }

      return marketingNdOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
