package com.customcomponent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customcomponent.vo.*;
import com.customcomponent.util.*;
import com.customcomponent.oauth.*;
import org.json.*;

public class MarketingItemSellinItComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingItemSellinItComponent.class);

  public static MarketingItemSellinItVO[] getItens(long solicitacaoDocumentId, Integer version) throws Exception {
    try {

      log.info(String
          .format("=*=*=*=*= brother-component | MarketingItemSellinItComponent | getItens 1"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,itensSellinIt,documentid,"
              + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingItemSellinItObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingItemSellinItObject.getJSONArray("content");

      MarketingItemSellinItVO[] MarketingItemSellinItVOArray =
          new MarketingItemSellinItVO[contentArray.length()];

      for (int i = 0; i < contentArray.length(); i++) {
        JSONObject itemJson =
            new JSONObject(contentArray.getJSONObject(i).optString("itemSellinIt_item"));

        ItemVO item = new ItemVO(itemJson.optString("codigo"), itemJson.optString("categoria"),
            itemJson.optString("descricao"), itemJson.optString("displaykey"), itemJson.optString("ccusto"));
        double rebateUnit = contentArray.getJSONObject(i).optDouble("itemSellinIt_rebateUnit");
        double qtde = contentArray.getJSONObject(i).optDouble("itemSellinIt_qtde");
        double rebateTotal = contentArray.getJSONObject(i).optDouble("itemSellinIt_rebateTotal");

        double qtdEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSellinIt_qtdEvidencia", 0);
        double valEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSellinIt_valEvidencia", 0);
        double totEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSellinIt_totEvidencia", 0);

        MarketingItemSellinItVOArray[i] = new MarketingItemSellinItVO(item, rebateUnit, qtde,
            rebateTotal, qtdEvidencia, valEvidencia, totEvidencia);
      }

      return MarketingItemSellinItVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
