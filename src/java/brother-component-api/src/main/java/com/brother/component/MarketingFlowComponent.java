package com.brother.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brother.vo.*;
import com.fluig.sdk.api.workflow.WorkflowVO;
import com.google.gson.Gson;
import com.brother.util.*;
import com.brother.oauth.*;
import org.json.*;
import java.lang.reflect.Field;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MarketingFlowComponent {

  private static Logger log = LoggerFactory.getLogger(MarketingFlowComponent.class);

  public static MarketingFlowVO getMarketingData(String guid) throws Exception {
    try {

      log.info(String.format(
          "=*=*=*=*= brother-component | MarketingFlowComponent | getMarketingData 1 | guid: %s",
          guid));

      String path =
          "/api/public/ecm/dataset/search?datasetId=fluig_busca_solicitacao_portal&filterFields=guid,"
              + guid;

      log.info(String.format(
          "=*=*=*=*= brother-component | MarketingFlowComponent | getMarketingData 2.1 v2 | path: %s",
          path));


      JSONObject marketingFlowObject =
          OAuthRequest.request(path, RestConstant.REQUEST_METHOD_GET, null);

      log.info(String
          .format("=*=*=*=*= brother-component | MarketingFlowComponent | getMarketingData 2.2"));


      JSONArray contentArray = marketingFlowObject.getJSONArray("content");

      log.info(String.format(
          "=*=*=*=*= brother-component | MarketingFlowComponent | getMarketingData 3 | guid: %s",
          guid));

      Long documentid = contentArray.getJSONObject(0).optLong("documentid");
      Integer version = contentArray.getJSONObject(0).optInt("version");
      Long folderAttach = contentArray.getJSONObject(0).optLong("folderAttach", 485); // 2438
      int solicitacao = contentArray.getJSONObject(0).optInt("solicitacao");
      String status = contentArray.getJSONObject(0).optString("status");
      double valorLiberado = contentArray.getJSONObject(0).optDouble("valorLiberado", 0);
      double valorTotalVerba = contentArray.getJSONObject(0).optDouble("valorTotalVerba", 0);
      double valorResultado = contentArray.getJSONObject(0).optDouble("valorResultado", 0);
      String descricaoDetalhada = contentArray.getJSONObject(0).optString("descricaoDetalhada");
      Long inicioAcao = contentArray.getJSONObject(0).optLong("inicioAcao");
      Long terminoAcao = contentArray.getJSONObject(0).optLong("terminoAcao");

      log.info(String.format(
          "=*=*=*=*= brother-component | MarketingFlowComponent | getMarketingData 3 | guid: %s",
          guid));

      Boolean envioEvidenciasConcluido =
          contentArray.getJSONObject(0).optBoolean("envioEvidenciasConcluido");
      Boolean evRecusada = contentArray.getJSONObject(0).optBoolean("evRecusada");
      String obsEnvioEvidencias = contentArray.getJSONObject(0).optString("obsEnvioEvidencias");
      Boolean envioNDConcluido = contentArray.getJSONObject(0).optBoolean("envioNDConcluido");
      Boolean ndRecusada = contentArray.getJSONObject(0).optBoolean("ndRecusada");
      String obsEnvioND = contentArray.getJSONObject(0).optString("obsEnvioND");
      int currentStepPortal = contentArray.getJSONObject(0).optInt("currentStepPortal", 0);
      String motivoCancelamento = contentArray.getJSONObject(0).optString("motivoCancelamento");
      String motivoRecusaND = contentArray.getJSONObject(0).optString("motivoRecusaND");
      String motivoRecusaEv = contentArray.getJSONObject(0).optString("motivoRecusaEv");
      String tipoSellout = contentArray.getJSONObject(0).optString("tipoSellout");
      String tipoPrpro = contentArray.getJSONObject(0).optString("tipoPrpro");
      String tipoQuantidade = contentArray.getJSONObject(0).optString("tipoQuantidade");

      log.info(String.format(
          "=*=*=*=*= brother-component | MarketingFlowComponent | getMarketingData 4 | guid: %s",
          guid));

      MarketingItemSelloutVO[] itensSellout =
          MarketingItemSelloutComponent.getItens(documentid, version);
      MarketingItemPrproVO[] itensPrpro =
          MarketingItemPrproComponent.getItens(documentid, version);

      MarketingItemSellinItVO[] itensSellinIt =
          MarketingItemSellinItComponent.getItens(documentid, version);
      MarketingItemSellinTgVO[] itensSellinTg =
          MarketingItemSellinTgComponent.getItens(documentid, version);
      MarketingItemSellinTgAcVO[] itensSellinTgAc =
          MarketingItemSellinTgAcComponent.getItens(documentid, version);

      MarketingItemSpiffItVO[] itensSpiffIt =
          MarketingItemSpiffItComponent.getItens(documentid, version);
      MarketingItemSpiffTgVO[] itensSpiffTg =
          MarketingItemSpiffTgComponent.getItens(documentid, version);
      MarketingItemVpcEvtVO[] itensVpcEvt =
          MarketingItemVpcEvtComponent.getItens(documentid, version);
      log.info(String.format(
          "=*=*=*=*= brother-component | MarketingFlowComponent | getMarketingData 4.3 | guid: %s",
          guid));
      MarketingItemVpcOutrosVO[] itensVpcOutros =
          MarketingItemVpcOutrosComponent.getItens(documentid, version);

      MarketingEvidenciaVO[] evidencias =
          MarketingEvidenciasComponent.getMarketingEvidencias(documentid, version);
      MarketingNdVO[] nd = MarketingNdComponent.getMarketingNd(documentid, version);
      MarketingDuplicatasVO[] duplicatas =
          MarketingDuplicatasComponent.getMarketingDuplicatas(documentid, version);

      return new MarketingFlowVO(documentid, solicitacao, status, valorLiberado, valorTotalVerba,
          valorResultado, descricaoDetalhada, inicioAcao, terminoAcao, envioEvidenciasConcluido,
          evRecusada, obsEnvioEvidencias, envioNDConcluido, ndRecusada, obsEnvioND,
          currentStepPortal, motivoCancelamento, motivoRecusaND, motivoRecusaEv, tipoSellout, tipoPrpro,
          tipoQuantidade, folderAttach, itensSellout, itensPrpro, itensSellinIt, itensSellinTg, itensSellinTgAc,
          itensSpiffIt, itensSpiffTg, itensVpcEvt, itensVpcOutros, evidencias, nd, duplicatas);

    } catch (Exception e) {
      throw e;
    }
  }

  public static ECMWorkflowVO getWorkflowVO(String guid, MarketingFlowVO solicitacao,
      Boolean completeTask) throws Exception {
    try {
      String tables =
          "emailsCliente;arquivosEvidencias;executivos;arquivosND;chat;rateioCategoria;duplicatas;itensSellinIt;itensSellinTgAc;itensSellinTg;itensSellout;itensSpiffIt;itensSpiffTg;statusErp;itensVpcEvt;itensVpcOutros";
      List<ECMFormDataVO> formData = ECMFormDataComponent.getFormData("marketing_abertura_verba",
          solicitacao.getDocumentid(), tables);

      List<String> selectedColleague = new ArrayList<>();
      ECMFormDataVO item;

      Field[] fields = solicitacao.getClass().getDeclaredFields();
      for (Field field : fields) {
        field.setAccessible(true);

        String value = "";
        try {
          value = field.get(solicitacao).toString();
        } catch (Exception e) {
        }

        ECMFormDataVO formDataVO = new ECMFormDataVO(field.getName(), value.toString());

        if (!formData.contains(formDataVO)) {
          formData.add(formDataVO);
        }

        item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName())).findFirst()
            .orElse(null);

        if (item != null) {
          item.setValue(formDataVO.getValue());
        } else {
          formData.add(formDataVO);
        }
      }

      int i = 0;

      if (solicitacao.getEvidencias() != null) {
        for (MarketingEvidenciaVO arquivo : solicitacao.getEvidencias()) {

          i++;

          fields = arquivo.getClass().getDeclaredFields();
          for (Field field : fields) {
            field.setAccessible(true);

            String value = "";
            try {
              value = field.get(arquivo).toString();
            } catch (Exception e) {
              log.info("ERRO field: " + field.getName());
            }

            ECMFormDataVO formDataVO = new ECMFormDataVO(
                "arquivoEv_" + field.getName() + "___" + String.valueOf(i), value);

            item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
                .findFirst().orElse(null);

            if (item != null) {
              item.setValue(formDataVO.getValue());
            } else {
              formData.add(formDataVO);
            }
          }
        }
      }

      if (solicitacao.getNd() != null) {
        i = 0;

        for (MarketingNdVO arquivoND : solicitacao.getNd()) {
          i++;

          fields = arquivoND.getClass().getDeclaredFields();
          for (Field field : fields) {
            field.setAccessible(true);

            String value = "";
            try {
              value = field.get(arquivoND).toString();
            } catch (Exception e) {
            }

            ECMFormDataVO formDataVO = new ECMFormDataVO(
                "arquivoND_" + field.getName() + "___" + String.valueOf(i), value);
            item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
                .findFirst().orElse(null);

            if (item != null) {
              item.setValue(formDataVO.getValue());
            } else {
              formData.add(formDataVO);
            }
          }
        }
      }

      if (solicitacao.getItensSellout() != null) {
        i = 0;

        for (MarketingItemSelloutVO itemSellout : solicitacao.getItensSellout()) {
          i++;

          fields = itemSellout.getClass().getDeclaredFields();
          for (Field field : fields) {
            field.setAccessible(true);

            String value = "";
            try {
              value = field.get(itemSellout).toString();
            } catch (Exception e) {
            }

            ECMFormDataVO formDataVO = new ECMFormDataVO(
                "itemSellout_" + field.getName() + "___" + String.valueOf(i), value);

            item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
                .findFirst().orElse(null);

            if (item != null) {
              item.setValue(formDataVO.getValue());
            } else {
              formData.add(formDataVO);
            }
          }
        }
      }

      // if (solicitacao.getItensPrpro() != null) {
      //   i = 0;

      //   for (MarketingItemPrproVO itemPrpro : solicitacao.getItensPrpro()) {
      //     i++;

      //     fields = itemPrpro.getClass().getDeclaredFields();
      //     for (Field field : fields) {
      //       field.setAccessible(true);

      //       String value = "";
      //       try {
      //         value = field.get(itemPrpro).toString();
      //       } catch (Exception e) {
      //       }

      //       ECMFormDataVO formDataVO = new ECMFormDataVO(
      //           "itemPrpro_" + field.getName() + "___" + String.valueOf(i), value);

      //       item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
      //           .findFirst().orElse(null);

      //       if (item != null) {
      //         item.setValue(formDataVO.getValue());
      //       } else {
      //         formData.add(formDataVO);
      //       }
      //     }
      //   }
      // }

      if (solicitacao.getItensSellinIt() != null) {
        i = 0;

        for (MarketingItemSellinItVO itemSellinIt : solicitacao.getItensSellinIt()) {
          i++;

          fields = itemSellinIt.getClass().getDeclaredFields();
          for (Field field : fields) {
            field.setAccessible(true);

            String value = "";
            try {
              value = field.get(itemSellinIt).toString();
            } catch (Exception e) {
            }

            ECMFormDataVO formDataVO = new ECMFormDataVO(
                "itemSellinIt_" + field.getName() + "___" + String.valueOf(i), value);

            item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
                .findFirst().orElse(null);

            if (item != null) {
              item.setValue(formDataVO.getValue());
            } else {
              formData.add(formDataVO);
            }
          }
        }
      }

      if (solicitacao.getItensSellinTg() != null) {


        i = 0;

        for (MarketingItemSellinTgVO itemSellinTg : solicitacao.getItensSellinTg()) {
          i++;

          fields = itemSellinTg.getClass().getDeclaredFields();
          for (Field field : fields) {
            field.setAccessible(true);

            String value = "";
            try {
              value = field.get(itemSellinTg).toString();
            } catch (Exception e) {
            }

            ECMFormDataVO formDataVO = new ECMFormDataVO(
                "itemSellinTg_" + field.getName() + "___" + String.valueOf(i), value);
            item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
                .findFirst().orElse(null);

            if (item != null) {
              item.setValue(formDataVO.getValue());
            } else {
              formData.add(formDataVO);
            }
          }
        }
      }

      if (solicitacao.getItensSellinTgAc() != null) {
        i = 0;

        for (MarketingItemSellinTgAcVO itemSellinTgAc : solicitacao.getItensSellinTgAc()) {
          i++;

          fields = itemSellinTgAc.getClass().getDeclaredFields();
          for (Field field : fields) {
            field.setAccessible(true);

            String value = "";
            try {
              value = field.get(itemSellinTgAc).toString();
            } catch (Exception e) {
            }

            ECMFormDataVO formDataVO = new ECMFormDataVO(
                "itemSellinTgAc_" + field.getName() + "___" + String.valueOf(i), value);
            item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
                .findFirst().orElse(null);

            if (item != null) {
              item.setValue(formDataVO.getValue());
            } else {
              formData.add(formDataVO);
            }
          }
        }
      }

      if (solicitacao.getItensSpiffIt() != null) {

        i = 0;

        for (MarketingItemSpiffItVO itemSpiffIt : solicitacao.getItensSpiffIt()) {
          i++;

          fields = itemSpiffIt.getClass().getDeclaredFields();
          for (Field field : fields) {
            field.setAccessible(true);

            String value = "";
            try {
              value = field.get(itemSpiffIt).toString();
            } catch (Exception e) {
            }

            ECMFormDataVO formDataVO = new ECMFormDataVO(
                "itemSpiffIt_" + field.getName() + "___" + String.valueOf(i), value);
            item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
                .findFirst().orElse(null);

            if (item != null) {
              item.setValue(formDataVO.getValue());
            } else {
              formData.add(formDataVO);
            }

          }
        }
      }

      if (solicitacao.getItensSpiffTg() != null) {
        i = 0;

        for (MarketingItemSpiffTgVO itemSpiffTg : solicitacao.getItensSpiffTg()) {
          i++;

          fields = itemSpiffTg.getClass().getDeclaredFields();
          for (Field field : fields) {
            field.setAccessible(true);

            String value = "";
            try {
              value = field.get(itemSpiffTg).toString();
            } catch (Exception e) {
            }

            ECMFormDataVO formDataVO = new ECMFormDataVO(
                "itemSpiffTg_" + field.getName() + "___" + String.valueOf(i), value);
            item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
                .findFirst().orElse(null);

            if (item != null) {
              item.setValue(formDataVO.getValue());
            } else {
              formData.add(formDataVO);
            }
          }

        }
      }

      if (solicitacao.getItensVpcEvt() != null) {

        i = 0;

        for (MarketingItemVpcEvtVO itemVpcEvt : solicitacao.getItensVpcEvt()) {
          i++;

          fields = itemVpcEvt.getClass().getDeclaredFields();
          for (Field field : fields) {
            field.setAccessible(true);

            String value = "";
            try {
              value = field.get(itemVpcEvt).toString();
            } catch (Exception e) {

            }

            ECMFormDataVO formDataVO = new ECMFormDataVO(
                "itemVpcEvt_" + field.getName() + "___" + String.valueOf(i), value);
            item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
                .findFirst().orElse(null);

            if (item != null) {
              item.setValue(formDataVO.getValue());
            } else {
              formData.add(formDataVO);
            }
          }
        }
      }

      if (solicitacao.getItensVpcOutros() != null) {

        i = 0;

        for (MarketingItemVpcOutrosVO itemVpcOutros : solicitacao.getItensVpcOutros()) {
          i++;

          fields = itemVpcOutros.getClass().getDeclaredFields();
          for (Field field : fields) {
            field.setAccessible(true);

            String value = "";
            try {
              value = field.get(itemVpcOutros).toString();
            } catch (Exception e) {
            }

            ECMFormDataVO formDataVO = new ECMFormDataVO(
                "itemVpcOutros_" + field.getName() + "___" + String.valueOf(i), value);
            item = formData.stream().filter(a -> a.getName().equals(formDataVO.getName()))
                .findFirst().orElse(null);

            if (item != null) {
              item.setValue(formDataVO.getValue());
            } else {
              formData.add(formDataVO);
            }
          }
        }
      }

      UserVO currentUser = UserComponent.getCurrentUser();

      ECMFormDataVO itemVersion =
          formData.stream().filter(a -> a.getName().equals("wfVersion")).findFirst().orElse(null);

      ECMFormDataVO itemVersionDoc = formData.stream()
          .filter(a -> a.getName().equals("metadata#version")).findFirst().orElse(null);
      ECMFormDataVO itemMovto = formData.stream().filter(a -> a.getName().equals("currentMovto"))
          .findFirst().orElse(null);

      int version = itemVersion != null ? Integer.parseInt(itemVersion.getValue()) : 0;
      int versionDoc = itemVersionDoc != null ? Integer.parseInt(itemVersionDoc.getValue()) : 0;
      int currentMovto = itemMovto != null ? Integer.parseInt(itemMovto.getValue()) : 0;

      int currentState = 0;
      int selectedState = 0;

      if (solicitacao.getCurrentStepPortal() == 4 || solicitacao.getCurrentStepPortal() == 2)
        completeTask = true;

      if (solicitacao.getCurrentStepPortal() == 1 || solicitacao.getCurrentStepPortal() == 2) {
        currentState = version < 16 ? 180 : 261; // 180
        selectedState = version < 16 ? currentState : completeTask ? 62 : currentState;
      }

      if (solicitacao.getCurrentStepPortal() == 3 || solicitacao.getCurrentStepPortal() == 4) {
        currentState = version < 16 ? 186 : 264; // 186
        selectedState = version < 16 ? currentState : completeTask ? 103 : currentState;
      }

      completeTask = version < 16 ? false : completeTask;
      // Boolean managerMode = version < 16 ? true : false;
      Boolean managerMode = true;

      // ECMWorkflowEngineService workflow = new ECMWorkflowEngineService();
      // workflow.saveAndSendTask(solicitacao.getSolicitacao(), activity);

      selectedColleague.add("System:Auto");

      ECMWorkflowVO wfVO = new ECMWorkflowVO("marketing_abertura_verba",
          solicitacao.getSolicitacao(), currentMovto, currentState, selectedState,
          currentUser.getCode(), "Solicitação movimentada pelo portal", selectedColleague, formData,
          completeTask, version, versionDoc);

      wfVO.setManagerMode(managerMode);

      return wfVO;


    } catch (Exception e) {
      throw e;
    }
  }

  public static MarketingFlowVO updateMarketingData(String guid, MarketingFlowVO solicitacao,
      Boolean completeTask) throws Exception {
    try {

      ECMWorkflowVO workflowVO = getWorkflowVO(guid, solicitacao, completeTask);

      Gson gson = new Gson();
      String json = gson.toJson(workflowVO);

      log.info(String.format(
          "=*=*=*=*= brother-component | MarketingFlowComponent | sendWorkflow | json: %s", json));

      JSONObject documentObject = OAuthRequest.request("/ecm/api/rest/ecm/workflowView/send",
          RestConstant.REQUEST_METHOD_POST, json);
      JSONObject contentObject = documentObject.getJSONObject("content");

      // Long documentid = contentObject.optLong("documentId");
      // String version = contentObject.optString("version");

      return solicitacao;

    } catch (

    Exception e) {
      log.info(e.getMessage());
      throw e;
    }
  }
}
