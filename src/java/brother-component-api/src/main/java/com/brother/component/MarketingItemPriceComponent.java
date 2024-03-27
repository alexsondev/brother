package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class MarketingItemPriceComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingItemPriceComponent.class);

  public static MarketingItemPriceVO[] getItens(long solicitacaoDocumentId, Integer version) throws Exception {
    try {

      log.info(
          String.format("=*=*=*=*= brother-component | MarketingitemPriceComponent | getItens"));

      String path =
          "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,itensPrice,documentid,"
          + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

      JSONObject MarketingItemPriceObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = MarketingItemPriceObject.getJSONArray("content");
      JSONArray newArray = new JSONArray();

      for (int i = 0; i < contentArray.length(); i++) {
        try {
          JSONObject itemJson =
              new JSONObject(contentArray.getJSONObject(i).optString("itemPrice_item"));
          newArray.put(contentArray.getJSONObject(i));
        } catch (Exception e) {}
      }

      MarketingItemPriceVO[] MarketingItemPriceVOArray =
          new MarketingItemPriceVO[newArray.length()];

      for (int i = 0; i < newArray.length(); i++) {
        try {

          JSONObject itemJson =
              new JSONObject(newArray.getJSONObject(i).optString("itemPrice_item"));

          ItemVO item = new ItemVO(itemJson.optString("codigo"), itemJson.optString("categoria"),
              itemJson.optString("descricao"), itemJson.optString("displaykey"), itemJson.optString("ccusto"));

          String finalidade = newArray.getJSONObject(i).optString("itemPrice_finalidade");
          String Price = newArray.getJSONObject(i).optString("itemPrice_Price");

          double rebateUnit = newArray.getJSONObject(i).optDouble("itemPrice_rebateUnit");
          double qtde = newArray.getJSONObject(i).optDouble("itemPrice_qtde");
          double rebateTotal = newArray.getJSONObject(i).optDouble("itemPrice_rebateTotal");

          double qtdEvidencia =
              newArray.getJSONObject(i).optDouble("itemPrice_qtdEvidencia", 0);
          double valEvidencia =
              newArray.getJSONObject(i).optDouble("itemPrice_valEvidencia", 0);
          double totEvidencia =
              newArray.getJSONObject(i).optDouble("itemPrice_totEvidencia", 0);

          MarketingItemPriceVOArray[i] = new MarketingItemPriceVO(item, finalidade, Price,
              rebateUnit, qtde, rebateTotal, qtdEvidencia, valEvidencia, totEvidencia);
        } catch (Exception e) {

        }
      }

      return MarketingItemPriceVOArray;


    } catch (Exception e) {
      throw e;
    }
  }
}
