package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class MarketingItemVpcEvtComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingItemVpcEvtComponent.class);

  public static MarketingItemVpcEvtVO[] getItens(long solicitacaoDocumentId, Integer version) throws Exception {
    try {

      log.info(
          String.format("=*=*=*=*= brother-component | MarketingItemVpcEvtComponent | getItens"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,itensVpcEvt,documentid,"
              + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingItemVpcEvtObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingItemVpcEvtObject.getJSONArray("content");

      MarketingItemVpcEvtVO[] MarketingItemVpcEvtVOArray =
          new MarketingItemVpcEvtVO[contentArray.length()];

      for (int i = 0; i < contentArray.length(); i++) {
        String nomeEvento = contentArray.getJSONObject(i).optString("itemVpcEvt_nomeEvento");
        String finalidade = contentArray.getJSONObject(i).optString("itemVpcEvt_finalidade");
        Long inicio = contentArray.getJSONObject(i).optLong("itemVpcEvt_inicio");
        Long termino = contentArray.getJSONObject(i).optLong("itemVpcEvt_termino");
        double perc = contentArray.getJSONObject(i).optDouble("itemVpcEvt_perc");
        double valorTotal = contentArray.getJSONObject(i).optDouble("itemVpcEvt_vlTotal");

        double qtdEvidencia = contentArray.getJSONObject(i).optDouble("itemVpcEvt_qtdEvidencia", 0);
        double valEvidencia = contentArray.getJSONObject(i).optDouble("itemVpcEvt_valEvidencia", 0);
        double totEvidencia = contentArray.getJSONObject(i).optDouble("itemVpcEvt_totEvidencia", 0);

        MarketingItemVpcEvtVOArray[i] = new MarketingItemVpcEvtVO(nomeEvento, finalidade, inicio,
            termino, perc, valorTotal, qtdEvidencia, valEvidencia, totEvidencia);
      }

      return MarketingItemVpcEvtVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
