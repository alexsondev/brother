package com.customcomponent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customcomponent.vo.*;
import com.customcomponent.util.*;
import com.customcomponent.oauth.*;
import org.json.*;

public class MarketingItemSpiffTgComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingItemSpiffTgComponent.class);

  public static MarketingItemSpiffTgVO[] getItens(long solicitacaoDocumentId, Integer version) throws Exception {
    try {

      log.info(
          String.format("=*=*=*=*= brother-component | MarketingItemSpiffTgComponent | getItens"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,itensSpiffTg,documentid,"
          + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingItemSpiffTgObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingItemSpiffTgObject.getJSONArray("content");

      MarketingItemSpiffTgVO[] MarketingItemSpiffTgVOArray =
          new MarketingItemSpiffTgVO[contentArray.length()];

      for (int i = 0; i < contentArray.length(); i++) {
        String foco = contentArray.getJSONObject(i).optString("itemSpiffTg_foco");
        double valorUnit = contentArray.getJSONObject(i).optDouble("itemSpiffTg_vlUnit");
        double qtde = contentArray.getJSONObject(i).optDouble("itemSpiffTg_qtde");
        double valorTotal = contentArray.getJSONObject(i).optDouble("itemSpiffTg_vlTotal");

        double qtdEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSpiffTg_qtdEvidencia", 0);
        double valEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSpiffTg_valEvidencia", 0);
        double totEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSpiffTg_totEvidencia", 0);

        MarketingItemSpiffTgVOArray[i] = new MarketingItemSpiffTgVO(foco, valorUnit, qtde,
            valorTotal, qtdEvidencia, valEvidencia, totEvidencia);
      }

      return MarketingItemSpiffTgVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
