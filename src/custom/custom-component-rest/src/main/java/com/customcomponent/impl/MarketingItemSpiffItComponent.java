package com.customcomponent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customcomponent.vo.*;
import com.customcomponent.util.*;
import com.customcomponent.oauth.*;
import org.json.*;

public class MarketingItemSpiffItComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingItemSpiffItComponent.class);

  public static MarketingItemSpiffItVO[] getItens(long solicitacaoDocumentId, Integer version) throws Exception {
    try {

      log.info(
          String.format("=*=*=*=*= brother-component | MarketingItemSpiffItComponent | getItens"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,itensSpiffIt,documentid,"
          + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingItemSpiffItObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingItemSpiffItObject.getJSONArray("content");

      MarketingItemSpiffItVO[] MarketingItemSpiffItVOArray =
          new MarketingItemSpiffItVO[contentArray.length()];

      for (int i = 0; i < contentArray.length(); i++) {
        JSONObject itemJson =
            new JSONObject(contentArray.getJSONObject(i).optString("itemSpiffIt_item"));

        ItemVO item = new ItemVO(itemJson.optString("codigo"), itemJson.optString("categoria"),
            itemJson.optString("descricao"), itemJson.optString("displaykey"), itemJson.optString("ccusto"));
        double valorUnit = contentArray.getJSONObject(i).optDouble("itemSpiffIt_spiffUnit");
        double qtde = contentArray.getJSONObject(i).optDouble("itemSpiffIt_qtde");
        double valorTotal = contentArray.getJSONObject(i).optDouble("itemSpiffIt_vlTotal");

        double qtdEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSpiffIt_qtdEvidencia", 0);
        double valEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSpiffIt_valEvidencia", 0);
        double totEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSpiffIt_totEvidencia", 0);

        MarketingItemSpiffItVOArray[i] = new MarketingItemSpiffItVO(item, valorUnit, qtde,
            valorTotal, qtdEvidencia, valEvidencia, totEvidencia);
      }

      return MarketingItemSpiffItVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
