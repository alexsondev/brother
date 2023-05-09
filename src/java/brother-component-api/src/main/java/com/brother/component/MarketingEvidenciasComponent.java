package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class MarketingEvidenciasComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingEvidenciasComponent.class);

  public static MarketingEvidenciaVO[] getMarketingEvidencias(long solicitacaoDocumentId,
      Integer version) throws Exception {
    try {

      log.info(String.format(
          "=*=*=*=*= brother-component | MarketingEvidenciasComponent | getMarketingEvidencias"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,arquivosEvidencias,documentid,"
              + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingEvidenciaObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingEvidenciaObject.getJSONArray("content");

      MarketingEvidenciaVO[] marketingEvidenciaVOArray =
          new MarketingEvidenciaVO[contentArray.length()];

      for (int i = 0; i < contentArray.length(); i++) {
        Long documentid = contentArray.getJSONObject(i).optLong("arquivoEv_documentid");
        String nome = contentArray.getJSONObject(i).optString("arquivoEv_nome");
        String tipo = contentArray.getJSONObject(i).optString("arquivoEv_tipo");
        String evversion = contentArray.getJSONObject(i).optString("arquivoEv_version");
        String url = contentArray.getJSONObject(i).optString("arquivoEv_url");
        String descricao = contentArray.getJSONObject(i).optString("arquivoEv_descricao");
        Boolean aceito = contentArray.getJSONObject(i).optBoolean("arquivoEv_aceito");
        String motivoRecusa = contentArray.getJSONObject(i).optString("arquivoEv_motivoRecusa");
        Boolean removed = contentArray.getJSONObject(i).optBoolean("arquivoEv_removed");


        marketingEvidenciaVOArray[i] = new MarketingEvidenciaVO(documentid, nome, tipo, evversion,
            url, descricao, aceito, motivoRecusa, removed);
      }

      return marketingEvidenciaVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
