package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;

public class ResumoVerbas {

  private static Logger log = LoggerFactory.getLogger(ResumoVerbas.class);

  public static ResumoVerbasVO[] getResumoVerbas(String guid) throws Exception {
    try {

      log.info(String
          .format("=*=*=*=*= brother-component | ResumoVerbas | getResumoVerbas | guid: %s", guid));

      String path =
          "/api/public/ecm/dataset/search?datasetId=fluig_busca_resumo_verbas&filterFields=guid,"
              + guid;

      JSONObject marketingFlowObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      JSONArray contentArray = marketingFlowObject.getJSONArray("content");

      ResumoVerbasVO[] resumoVerbasVOArray = new ResumoVerbasVO[contentArray.length()];

      for (int i = 0; i < contentArray.length(); i++) {
        Long documentid = contentArray.getJSONObject(i).optLong("documentid");
        String solicitacao = contentArray.getJSONObject(i).optString("solicitacao");
        String nomeAcao = contentArray.getJSONObject(i).optString("nomeAcao");
        Long prazoVencto = contentArray.getJSONObject(i).optLong("prazoVencto");
        String tipoAcaoDescricao = contentArray.getJSONObject(i).optString("tipoAcaoDescricao");
        String produtosCodigos = contentArray.getJSONObject(i).optString("produtosCodigos");
        String clienteCodigo = contentArray.getJSONObject(i).optString("clienteCodigo");
        String clienteNome = contentArray.getJSONObject(i).optString("clienteNome");
        Long inicioAcao = contentArray.getJSONObject(i).optLong("inicioAcao");
        Long terminoAcao = contentArray.getJSONObject(i).optLong("terminoAcao");
        String status = contentArray.getJSONObject(i).optString("status");
        String guidSolicitacao = contentArray.getJSONObject(i).optString("guid");

        resumoVerbasVOArray[i] = new ResumoVerbasVO(documentid, solicitacao, nomeAcao, prazoVencto,
            tipoAcaoDescricao, produtosCodigos, clienteCodigo, clienteNome, inicioAcao, terminoAcao,
            status, guidSolicitacao);
      }

      return resumoVerbasVOArray;

    } catch (Exception e) {
      throw e;
    }
  }
}
