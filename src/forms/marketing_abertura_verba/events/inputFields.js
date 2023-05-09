/*eslint-disable*/
/*jshint -W116 */
function inputFields(form) {

  const Params = getParams(form);

  const currentState = getValue("WKNumState");
  const nextState = getValue("WKNextState");

  const clienteNome = value(form, `clienteNome`);
  const nomeAcao = value(form, `nomeAcao`);
  const solicitacao = value(form, `solicitacao`);
  const tipoAcaoDescricao = value(form, `tipoAcaoDescricao`);
  const suspenderAcao = value(form, `suspenderAcao`);

  const arquivosEvidencias = getChildren(form, `arquivosEvidencias`,
    [`arquivoEv_nome`, `arquivoEv_type`, `arquivoEv_documentid`, `arquivoEv_version`,
      `arquivoEv_url`, `arquivoEv_removed`, `arquivoEv_descricao`, `arquivoEv_aceito`,
      `arquivoEv_motivoRecusa`]);

  const arquivosND = getChildren(form, `arquivosND`,
    [`arquivoND_nome`, `arquivoND_type`, `arquivoND_documentid`, `arquivoND_version`,
      `arquivoND_url`, `arquivoND_removed`, `arquivoND_descricao`, `arquivoND_aceito`,
      `arquivoND_motivoRecusa`, `arquivoND_numero`]);

  const displaykey = `${suspenderAcao ? 'SUSPENSA - ' : ''} ${clienteNome}`;

  log.info(`getValue('WKUser') = ${getValue('WKUser')}`);

  const dsUser = getDataset('colleague', ['colleagueId', 'colleagueName', 'mail', 'login'], [
    { field: 'login', value: String(getValue('WKUser')) }
  ], true)[0];

  form.setValue('displaykey', displaykey);

  atualizaPendenteTotvs(form);

  if (currentState == Params.atividades.validarMarketing[0]) {
    if (nextState == Params.atividades.gtwAprovarGerMarketing[0]) {
      form.setValue('statusValidacaoMarketing', 'APROVADO');
    }
    if (nextState == Params.atividades.revisarSolicitacao[0]) {
      form.setValue('statusValidacaoMarketing', 'REPROVADO');
    }

    form.setValue('userValMarketing', JSON.stringify(dsUser));
    form.setValue('dataValidacaoMarketing', new Date().getTime());
  }

  if (currentState == Params.atividades.aprovarGerMarketing[0]) {
    if (nextState == Params.atividades.aprovarPresidencia[0]) {
      form.setValue('statusAprovGerMarketing', 'APROVADO');
    }
    if (nextState == Params.atividades.validarMarketing[0]) {
      form.setValue('statusAprovGerMarketing', 'REPROVADO');
    }

    form.setValue('userAprovGerMarketing', JSON.stringify(dsUser));
    form.setValue('dataAprovGerMarketing', new Date().getTime());
  }

  if (currentState == Params.atividades.aprovarPresidencia[0]) {

    if (nextState == Params.atividades.notificarGrupoBrotherInicio[0]) {
      form.setValue('statusAprovPresidenciaVp', 'APROVADO');
    }
    if (nextState == Params.atividades.revisarSolicitacao[0]) {
      form.setValue('statusAprovPresidenciaVp', 'REPROVADO');
    }

    form.setValue('userAprovPresidenciaVp', JSON.stringify(dsUser));
    form.setValue('dataAprovPresidenciaVp', new Date().getTime());
  }

  if (currentState == Params.atividades.validarEvidencias[0]) {
    if (nextState == Params.atividades.gtwAprovarVerbaMaior[0] || nextState == Params.atividades.conferirFinanceiro[0]) {
      form.setValue('statusValidacaoEvid', 'APROVADO');
    }
    if (nextState == Params.atividades.enviarEvidencias[0]) {
      form.setValue('statusValidacaoEvid', 'REPROVADO');
    }
  }

  if (currentState == Params.atividades.validarND[0]) {
    if (nextState == Params.atividades.conferirFinanceiro[0]) {
      form.setValue('statusValidacaoND', 'APROVADO');
    }
    if (nextState == Params.atividades.enviarND[0]) {
      form.setValue('statusValidacaoND', 'REPROVADO');
    }
  }

  if (currentState == Params.atividades.aprovarVerbaMaior[0]) {
    if (nextState == Params.atividades.enviarND[0]) {
      form.setValue('statusAprovVerbaMaior', 'APROVADO');
    }
    if (nextState == Params.atividades.validarEvidencias[0]) {
      form.setValue('statusAprovVerbaMaior', 'REPROVADO');
    }

    form.setValue('userAprovVerbaMaior', JSON.stringify(dsUser));
    form.setValue('dataAprovVerbaMaior', new Date().getTime());
  }

  if (currentState == Params.atividades.aprovarVerbaMenor[0]) {
    if (nextState == Params.atividades.enviarND[0]) {
      form.setValue('statusAprovVerbaMenor', 'APROVADO');
    }
    if (nextState == Params.atividades.validarEvidencias[0]) {
      form.setValue('statusAprovVerbaMenor', 'REPROVADO');
    }

    form.setValue('userAprovVerbaMenor', JSON.stringify(dsUser));
    form.setValue('dataAprovVerbaMenor', new Date().getTime());
  }

  if (currentState == Params.atividades.conferirFinanceiro[0]) {
    if (nextState == Params.atividades.aprovarPagamento[0]) {
      form.setValue('statusFinanceiro', 'APROVADO');
    }
    if (nextState == Params.atividades.validarEvidencias[0]) {
      form.setValue('statusFinanceiro', 'REPROVADO');
    }
  }

  if (currentState == Params.atividades.aprovarPagamento[0]) {
    if (nextState == Params.atividades.gerarAbatimentos[0]) {
      form.setValue('statusAprovPagamento', 'APROVADO');
    }
    if (nextState == Params.atividades.conferirFinanceiro[0]) {
      form.setValue('statusAprovPagamento', 'REPROVADO');
    }

    form.setValue('userAprovPagamento', JSON.stringify(dsUser));
    form.setValue('dataAprovPagamento', new Date().getTime());
  }
}

function atividade(num, name) {
  const Params = getParams(form);

  return Params.atividades[name].indexOf(num) > -1;

}