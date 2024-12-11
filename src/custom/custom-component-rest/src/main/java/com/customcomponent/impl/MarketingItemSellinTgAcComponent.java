package com.customcomponent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customcomponent.vo.*;
import com.customcomponent.util.*;
import com.customcomponent.oauth.*;
import org.json.*;

public class MarketingItemSellinTgAcComponent {

    private static Logger log = LoggerFactory.getLogger(MarketingItemSellinTgAcComponent.class);

    public static MarketingItemSellinTgAcVO[] getItens(long solicitacaoDocumentId, Integer version)
            throws Exception {
        try {

            log.info(String.format(
                    "=*=*=*=*= brother-component | MarketingItemSellinTgAcComponent | getItens"));

            String path =
                    "/api/public/ecm/dataset/search?datasetId=marketing_abertura_verba&filterFields=tablename,itensSellinTgAc,documentid,"
                    + String.valueOf(solicitacaoDocumentId) + ",version," + String.valueOf(version);

            JSONObject MarketingItemSellinTgAcObject =
                    OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

            JSONArray contentArray = MarketingItemSellinTgAcObject.getJSONArray("content");

            MarketingItemSellinTgAcVO[] MarketingItemSellinTgAcVOArray =
                    new MarketingItemSellinTgAcVO[contentArray.length()];

            for (int i = 0; i < contentArray.length(); i++) {
                String descricao =
                        contentArray.getJSONObject(i).optString("itemSellinTgAc_descricao");
                double valorUnit =
                        contentArray.getJSONObject(i).optDouble("itemSellinTgAc_vlTarget");
                double qtde = contentArray.getJSONObject(i).optDouble("itemSellinTgAc_qtde");
                double valorTotal =
                        contentArray.getJSONObject(i).optDouble("itemSellinTgAc_vlTotal");

                double qtdEvidencia =
                        contentArray.getJSONObject(i).optDouble("itemSellinTgAc_qtdEvidencia", 0);
                double valEvidencia =
                        contentArray.getJSONObject(i).optDouble("itemSellinTgAc_valEvidencia", 0);
                double totEvidencia =
                        contentArray.getJSONObject(i).optDouble("itemSellinTgAc_totEvidencia", 0);

                MarketingItemSellinTgAcVOArray[i] = new MarketingItemSellinTgAcVO(descricao,
                        valorUnit, qtde, valorTotal, qtdEvidencia, valEvidencia, totEvidencia);
            }

            return MarketingItemSellinTgAcVOArray;


        } catch (Exception e) {
            throw e;
        }
    }
}
