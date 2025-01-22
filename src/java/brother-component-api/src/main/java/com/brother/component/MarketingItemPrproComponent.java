package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class MarketingItemPrproComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingItemPrproComponent.class);

  public static MarketingItemPrproVO[] getItens(long solicitacaoDocumentId, Integer version) throws Exception {
    try {

      log.info(
          String.format("=*=*=*=*= brother-component | MarketingitemPrproComponent | getItens"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,itensPrpro,documentid,"
          + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingItemPrproObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingItemPrproObject.getJSONArray("content");
      JSONArray newArray = new JSONArray();

      for (int i = 0; i < contentArray.length(); i++) {
        try {
          JSONObject itemJson =
              new JSONObject(contentArray.getJSONObject(i).optString("itemPrpro_item"));
          newArray.put(contentArray.getJSONObject(i));
        } catch (Exception e) {}
      }

      MarketingItemPrproVO[] MarketingItemPrproVOArray =
          new MarketingItemPrproVO[newArray.length()];

      for (int i = 0; i < newArray.length(); i++) {
        try {

          JSONObject itemJson =
              new JSONObject(newArray.getJSONObject(i).optString("itemPrpro_item"));

          ItemVO item = new ItemVO(itemJson.optString("codigo"), itemJson.optString("categoria"),
              itemJson.optString("descricao"), itemJson.optString("displaykey"), itemJson.optString("ccusto"));

          String finalidade = newArray.getJSONObject(i).optString("itemPrpro_finalidade");
          String Prpro = newArray.getJSONObject(i).optString("itemPrpro_Prpro");

          double rebateUnit = newArray.getJSONObject(i).optDouble("itemPrpro_rebateUnit");
          double qtde = newArray.getJSONObject(i).optDouble("itemPrpro_qtde");
          double rebateTotal = newArray.getJSONObject(i).optDouble("itemPrpro_rebateTotal");

          double qtdEvidencia =
              newArray.getJSONObject(i).optDouble("itemPrpro_qtdEvidencia", 0);
          double valEvidencia =
              newArray.getJSONObject(i).optDouble("itemPrpro_valEvidencia", 0);
          double totEvidencia =
              newArray.getJSONObject(i).optDouble("itemPrpro_totEvidencia", 0);

          MarketingItemPrproVOArray[i] = new MarketingItemPrproVO(item, finalidade, Prpro,
              rebateUnit, qtde, rebateTotal, qtdEvidencia, valEvidencia, totEvidencia);
        } catch (Exception e) {

        }
      }

      return MarketingItemPrproVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
