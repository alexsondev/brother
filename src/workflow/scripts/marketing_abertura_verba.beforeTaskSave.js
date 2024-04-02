function beforeTaskSave(colleagueId, nextSequenceId, userList) {

  log.info("beforeTaskSave ini")
  var Params = params();

  var sequenceId = getValue("WKNumState");
  var comments = getValue("WKUserComment");
  var obsValidacaoMarketing =  hAPI.getCardValue("obsValidacaoMarketing");
  var obsAprovPresidenciaVp =  hAPI.getCardValue("obsAprovPresidenciaVp");
  var obsAprovGerMarketing =  hAPI.getCardValue("obsAprovGerMarketing");


  if (comments && comments != '') {
    if (Params.atividades.aprovarGerMarketing.indexOf(Number(sequenceId)) >= 0) {
      if (Params.atividades.validarMarketing.indexOf(Number(nextSequenceId)) >= 0) {
        hAPI.setCardValue("obsAprovGerMarketing", getValue("WKUserComment"));
      }
    }

  
    if (Params.atividades.validarMarketing.indexOf(Number(sequenceId)) >= 0) {
      if (Params.atividades.revisarSolicitacao.indexOf(Number(nextSequenceId)) >= 0) {
        hAPI.setCardValue("obsValidacaoMarketing", getValue("WKUserComment"));
      }
    } 
  

    if (Params.atividades.aprovarPresidencia.indexOf(Number(sequenceId)) >= 0) {
      if (Params.atividades.validarMarketing.indexOf(Number(nextSequenceId)) >= 0) {
        hAPI.setCardValue("obsAprovPresidenciaVp", getValue("WKUserComment"));
      }
    }

    if (Params.atividades.aprovarVerbaMaior.indexOf(Number(sequenceId)) >= 0) {
      if (Params.atividades.validarEvidencias.indexOf(Number(nextSequenceId)) >= 0) {
        hAPI.setCardValue("obsAprovVerbaMaior", getValue("WKUserComment"));
      }
    }

    if (Params.atividades.aprovarVerbaMenor.indexOf(Number(sequenceId)) >= 0) {
      if (Params.atividades.validarEvidencias.indexOf(Number(nextSequenceId)) >= 0) {
        hAPI.setCardValue("obsAprovVerbaMenor", getValue("WKUserComment"));
      }
    }

    if (Params.atividades.aprovarPagamento.indexOf(Number(sequenceId)) >= 0) {
      if (Params.atividades.conferirFinanceiro.indexOf(Number(nextSequenceId)) >= 0) {
        hAPI.setCardValue("obsAprovPagamento", getValue("WKUserComment"));
      }
    }

    if (Params.atividades.gerarAbatimentos.indexOf(Number(nextSequenceId)) >= 0) {
      notificaAprovacaoPagamento();
    }

    if (Params.atividades.validarMarketing.indexOf(Number(nextSequenceId)) >= 0) {
      if (hAPI.getCardValue("dataAprovPresidenciaVp") != ""
        && hAPI.getCardValue("dataAprovPresidenciaVp") != "null"
        && hAPI.getCardValue("dataAprovPresidenciaVp") != null) {
        hAPI.setCardValue("pendenteTotvs", "S");
        atualizaStatus();
      }
    }

  }
  else{  
     //devolve para solicitante
     if (Params.atividades.validarMarketing.indexOf(Number(sequenceId)) >= 0) {
       if ((Params.atividades.revisarSolicitacao.indexOf(Number(nextSequenceId)) >= 0) && (obsValidacaoMarketing == '')){
          throw 'Observacao obrigatorio em Validar Marketing';
        }
      } 
      // devolve da gerencia para marketing
      if (Params.atividades.aprovarGerMarketing.indexOf(Number(sequenceId)) >= 0) {
        if ((Params.atividades.validarMarketing.indexOf(Number(nextSequenceId)) >= 0) && (obsAprovGerMarketing == '')){
           throw 'O campo observações, em Aprovação Gerência de Marketing, é obrigatório ';
        }
      }
      // devolve da presidência para o marketing
      if (Params.atividades.aprovarPresidencia.indexOf(Number(sequenceId)) >= 0) {
        if ((Params.atividades.validarMarketing.indexOf(Number(nextSequenceId)) >= 0) && (obsAprovPresidenciaVp == '')){
            throw 'O campo observações, em Aprovação Presidência, é obrigatório';
        }
      }
    }
    
}
