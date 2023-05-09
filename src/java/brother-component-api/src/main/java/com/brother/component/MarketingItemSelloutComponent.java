package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class MarketingItemSelloutComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingItemSelloutComponent.class);

  public static MarketingItemSelloutVO[] getItens(long solicitacaoDocumentId, Integer version) throws Exception {
    try {

      log.info(
          String.format("=*=*=*=*= brother-component | MarketingitemSelloutComponent | getItens"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,itensSellout,documentid,"
          + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingItemSelloutObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingItemSelloutObject.getJSONArray("content");
      JSONArray newArray = new JSONArray();

      for (int i = 0; i < contentArray.length(); i++) {
        try {
          JSONObject itemJson =
              new JSONObject(contentArray.getJSONObject(i).optString("itemSellout_item"));
          newArray.put(contentArray.getJSONObject(i));
        } catch (Exception e) {}
      }

      MarketingItemSelloutVO[] MarketingItemSelloutVOArray =
          new MarketingItemSelloutVO[newArray.length()];

      for (int i = 0; i < newArray.length(); i++) {
        try {

          JSONObject itemJson =
              new JSONObject(newArray.getJSONObject(i).optString("itemSellout_item"));

          ItemVO item = new ItemVO(itemJson.optString("codigo"), itemJson.optString("categoria"),
              itemJson.optString("descricao"), itemJson.optString("displaykey"), itemJson.optString("ccusto"));

          String finalidade = newArray.getJSONObject(i).optString("itemSellout_finalidade");
          String target = newArray.getJSONObject(i).optString("itemSellout_target");

          double rebateUnit = newArray.getJSONObject(i).optDouble("itemSellout_rebateUnit");
          double qtde = newArray.getJSONObject(i).optDouble("itemSellout_qtde");
          double rebateTotal = newArray.getJSONObject(i).optDouble("itemSellout_rebateTotal");

          double qtdEvidencia =
              newArray.getJSONObject(i).optDouble("itemSellout_qtdEvidencia", 0);
          double valEvidencia =
              newArray.getJSONObject(i).optDouble("itemSellout_valEvidencia", 0);
          double totEvidencia =
              newArray.getJSONObject(i).optDouble("itemSellout_totEvidencia", 0);

          MarketingItemSelloutVOArray[i] = new MarketingItemSelloutVO(item, finalidade, target,
              rebateUnit, qtde, rebateTotal, qtdEvidencia, valEvidencia, totEvidencia);
        } catch (Exception e) {

        }
      }

      return MarketingItemSelloutVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
