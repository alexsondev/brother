package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class MarketingItemVpcOutrosComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingItemVpcOutrosComponent.class);

  public static MarketingItemVpcOutrosVO[] getItens(long solicitacaoDocumentId, Integer version) throws Exception {
    try {

      log.info(String
          .format("=*=*=*=*= brother-component | MarketingItemVpcOutrosComponent | getItens"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,itensVpcOutros,documentid,"
          + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingItemVpcOutrosObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingItemVpcOutrosObject.getJSONArray("content");

      MarketingItemVpcOutrosVO[] MarketingItemVpcOutrosVOArray =
          new MarketingItemVpcOutrosVO[contentArray.length()];

      for (int i = 0; i < contentArray.length(); i++) {
        String tipo = contentArray.getJSONObject(i).optString("itemVpcOutros_tipo");
        String finalidade = contentArray.getJSONObject(i).optString("itemVpcOutros_finalidade");
        double qtde = contentArray.getJSONObject(i).optDouble("itemVpcOutros_qtde");
        double perc = contentArray.getJSONObject(i).optDouble("itemVpcOutros_perc");
        double valorTotal = contentArray.getJSONObject(i).optDouble("itemVpcOutros_vlTotal");

        double qtdEvidencia =
            contentArray.getJSONObject(i).optDouble("itemVpcOutros_qtdEvidencia", 0);
        double valEvidencia =
            contentArray.getJSONObject(i).optDouble("itemVpcOutros_valEvidencia", 0);
        double totEvidencia =
            contentArray.getJSONObject(i).optDouble("itemVpcOutros_totEvidencia", 0);

        MarketingItemVpcOutrosVOArray[i] = new MarketingItemVpcOutrosVO(tipo, finalidade, qtde,
            perc, valorTotal, qtdEvidencia, valEvidencia, totEvidencia);
      }

      return MarketingItemVpcOutrosVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
