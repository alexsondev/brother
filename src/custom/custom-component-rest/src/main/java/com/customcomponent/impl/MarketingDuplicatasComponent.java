package com.customcomponent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customcomponent.vo.*;
import com.customcomponent.util.*;
import com.customcomponent.oauth.*;
import org.json.*;

public class MarketingDuplicatasComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingDuplicatasComponent.class);

  public static MarketingDuplicatasVO[] getMarketingDuplicatas(long solicitacaoDocumentId,
      Integer version) throws Exception {
    try {

      log.info(String.format(
          "=*=*=*=*= brother-component | MarketingDuplicatasComponent | getMarketingDuplicatas"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,duplicatas,documentid,"
              + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingDuplicatasObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingDuplicatasObject.getJSONArray("content");

      MarketingDuplicatasVO[] marketingDuplicatasVOArray =
          new MarketingDuplicatasVO[contentArray.length()];

      for (int i = 0; i < contentArray.length(); i++) {
        String titulo = contentArray.getJSONObject(i).optString("titulo_tituloParcela");
        double valorAntecipa = contentArray.getJSONObject(i).optDouble("titulo_valorAntecipa");
        String dataVencto = contentArray.getJSONObject(i).optString("titulo_dataVencto");
        double valorSaldo = contentArray.getJSONObject(i).optDouble("titulo_valorSaldo");
        double saldoAposAbatimento =
            contentArray.getJSONObject(i).optDouble("titulo_saldoAposAbatimento");
        String nd = contentArray.getJSONObject(i).optString("titulo_nd_f");

        marketingDuplicatasVOArray[i] = new MarketingDuplicatasVO(titulo, valorAntecipa, dataVencto,
            valorSaldo, saldoAposAbatimento, nd);
      }

      return marketingDuplicatasVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
