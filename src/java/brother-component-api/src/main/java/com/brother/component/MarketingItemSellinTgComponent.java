package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class MarketingItemSellinTgComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingItemSellinTgComponent.class);

  public static MarketingItemSellinTgVO[] getItens(long solicitacaoDocumentId, Integer version) throws Exception {
    try {

      log.info(String
          .format("=*=*=*=*= brother-component | MarketingItemSellinTgComponent | getItens 1"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,itensSellinTg,documentid,"
          + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingItemSellinTgObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingItemSellinTgObject.getJSONArray("content");

      MarketingItemSellinTgVO[] MarketingItemSellinTgVOArray =
          new MarketingItemSellinTgVO[contentArray.length()];

      for (int i = 0; i < contentArray.length(); i++) {
        String descricao = contentArray.getJSONObject(i).optString("itemSellinTg_descricao");
        double valorUnit = contentArray.getJSONObject(i).optDouble("itemSellinTg_vlTarget");
        double qtde = contentArray.getJSONObject(i).optDouble("itemSellinTg_qtde");
        double valorTotal = contentArray.getJSONObject(i).optDouble("itemSellinTg_vlTotal");
        double qtdEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSellinTg_qtdEvidencia", 0);
        double valEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSellinTg_valEvidencia", 0);
        double totEvidencia =
            contentArray.getJSONObject(i).optDouble("itemSellinTg_totEvidencia", 0);

        MarketingItemSellinTgVOArray[i] = new MarketingItemSellinTgVO(descricao, valorUnit, qtde,
            valorTotal, qtdEvidencia, valEvidencia, totEvidencia);
      }

      return MarketingItemSellinTgVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
