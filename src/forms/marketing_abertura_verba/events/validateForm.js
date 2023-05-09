/*eslint-disable*/
/*jshint -W116 */
function validateForm(form) {
  const Errors = value(form, `Errors`) || [];
  const regras = value(form, `regras`) || [];
  const Params = getParams(form);

  const emailsCliente = getChildren(form, `emailsCliente`,
    [`email_email`, `email_iniAcao`, `email_fimAcao`, `email_evidencia`,
      `email_envioND`, `email_pagamento`]);

  const arquivosEvidencias = getChildren(form, `arquivosEvidencias`,
    [`arquivoEv_nome`, `arquivoEv_type`, `arquivoEv_documentid`, `arquivoEv_version`,
      `arquivoEv_url`, `arquivoEv_removed`, `arquivoEv_descricao`, `arquivoEv_aceito`]);

  const arquivosND = getChildren(form, `arquivosND`,
    [`arquivoND_nome`, `arquivoND_type`, `arquivoND_documentid`, `arquivoND_version`,
      `arquivoND_url`, `arquivoND_removed`, `arquivoND_descricao`, `arquivoND_aceito`, `arquivoND_numero`]);

  const duplicatas = getChildren(form, `duplicatas`,
    [`titulo_seq`, `titulo_codCliente`, `titulo_codEspec`, `titulo_codSerie`,
      `titulo_matriz`, `titulo_dataEmissao`, `titulo_dataVencto`, `titulo_numTitulo`,
      `titulo_parcela`, `titulo_valorOriginal`, `titulo_valorSaldo`, `titulo_valorAntecipa`]);

  const executivos = getChildren(form, `executivos`,
    [`executivo_executivo`, `executivo_codigo`]);

  const importado = value(form, `importado`);
  const clienteCodigo = value(form, `clienteCodigo`);
  const tipoAcaoCodigo = value(form, `tipoAcaoCodigo`);
  const inicioAcao = value(form, `inicioAcao`);
  const terminoAcao = value(form, `terminoAcao`);
  const tipoQuantidade = value(form, `tipoQuantidade`);
  const tipoSellin = value(form, `tipoSellin`);
  const tipoVpc = value(form, `tipoVpc`);
  const tipoSpiff = value(form, `tipoSpiff`);
  const descricaoDetalhada = value(form, `descricaoDetalhada`);
  const totalRateio = value(form, `totalRateio`);
  const nomeAcao = value(form, `nomeAcao`);
  const motivoRecusaEv = value(form, `motivoRecusaEv`);
  const motivoRecusaND = value(form, `motivoRecusaND`);

  const anteciparEncerramento = value(form, `anteciparEncerramento`);
  const obsEncerramentoAntecip = value(form, `obsEncerramentoAntecip`);

  const obsValidacaoMarketing = value(form, `obsValidacaoMarketing`);

  const obsAprovGerMarketing = value(form, `obsAprovGerMarketing`);
  const obsAprovPresidenciaVp = value(form, `obsAprovPresidenciaVp`);
  const obsAprovVerbaMaior = value(form, `obsAprovVerbaMaior`);
  const obsAprovVerbaMenor = value(form, `obsAprovVerbaMenor`);

  const valorResultado = value(form, `valorResultado`);
  const obsEnvioEvidencias = value(form, `obsEnvioEvidencias`);
  const envioEvidenciasConcluido = value(form, `envioEvidenciasConcluido`);

  const obsConferenciaFinanceiro = value(form, `obsConferenciaFinanceiro`);
  const difValorLiberado = value(form, `difValorLiberado`);
  const obsAprovPagamento = value(form, `obsAprovPagamento`);
  const suspenderAcao = value(form, `suspenderAcao`);

  const currentState = getValue(`WKNumState`);
  const nextState = getValue(`WKNextState`);
  const completeTask = getValue("WKCompletTask") == 'true';
  const managerMode = getValue("WKManagerMode") == 'true';
  const comments = getValue("WKUserComment");

  let nextStateTxt = ''
  let currentStateTxt = ''

  for (var atividade in Params.atividades) {
    if (Params.atividades[atividade].indexOf(parseInt(currentState)) > -1) {
      currentStateTxt = atividade;
    }
    if (Params.atividades[atividade].indexOf(parseInt(nextState)) > -1) {
      nextStateTxt = atividade;
    }
  }

  if (regras.enableEncerramentoAntecipado) {
    if (anteciparEncerramento && !obsEncerramentoAntecip) {
      Errors.push(`Informe o motivo do encerramento antecipado`);
    }
  }

  if (currentState !== nextState) {

    if (suspenderAcao && !managerMode) {
      Errors.push(`A ação está suspensa e não poderá ser alterada. Em caso de dúvidas, entre em contato com o gestor do processo.`);
    }

    if (managerMode && suspenderAcao && completeTask) {
      Errors.push(`A ação está (sendo) suspensa. Salve a ação ao invés de Enviar.`);
    }

    if (regras.enableSolicitacao || importado) {

      if (!executivos || executivos.length == 0) {
        Errors.push(`Informe o executivo`);
      }

      executivos.forEach((e, i) => {
        if (!e.executivo_codigo) {
          Errors.push(`Informe o executivo na linha ${i + 1}`);
        }
      })

      if (!clienteCodigo) {
        Errors.push(`Informe o cliente`);
      }

      if (!nomeAcao) {
        Errors.push(`Informe o nome da ação`);
      }

      if (!tipoAcaoCodigo) {
        Errors.push(`Informe o tipo da ação`);
      }

      if (!inicioAcao) {
        Errors.push(`Informe a data de início da ação`);
      }

      if (!terminoAcao) {
        Errors.push(`Informe a data fim da ação`);
      }

      if (!tipoQuantidade) {
        Errors.push(`Informe a quantidade (Limitada / Estimada)`);
      }

      if (tipoAcaoCodigo == `sellin`) {
        if (!tipoSellin) {
          Errors.push(`Informe o tipo de Sellin (Item / Target)`);
        }
      }

      if (tipoAcaoCodigo == `vpc`) {
        if (!tipoVpc) {
          Errors.push(`Informe o tipo de VPC (Eventos / Outros)`);
        }
      }

      if (tipoAcaoCodigo == `spiff`) {
        if (!tipoSpiff) {
          Errors.push(`Informe o tipo de Spiff (Item / Target)`);
        }
      }

      if (!descricaoDetalhada) {
        Errors.push(`Informe a descrição detalhada da ação`);
      }

      if (totalRateio != `1`) {
        Errors.push(`Informe o rateio por categoria totalizando 100%`);
      }
    }

    if (currentStateTxt == 'validarMarketing') {
      // 1. Enviar para Aprovação
      if (nextStateTxt == `gtwAprovarGerMarketing`) {
        // if (!executivoCodigo) {
        //   Errors.push(`Informe o executivo de contas`);
        // }
      }
      // 2. Devolver para Solicitante
      if (nextStateTxt == `revisarSolicitacao`) {
        if (!obsValidacaoMarketing && !comments) {
          // Errors.push(`Informe o motivo da reprovação 1`);
        }
      }
    }

    if (currentStateTxt == 'aprovarGerMarketing') {
      // 1. Aprovar
      if (nextStateTxt == `aprovarPresidencia`) {

      }
      // 2. Reprovar
      if (nextStateTxt == `validarMarketing`) {
        if (!obsAprovGerMarketing) {
          // Errors.push(`Informe o motivo da reprovação 2`);
        }
      }
    }

    if (currentStateTxt == 'aprovarPresidencia') {
      // 1. Aprovar
      if (nextStateTxt == `notificarGrupoBrotherInicio`) {

      }
      // 2. Reprovar
      if (nextStateTxt == `revisarSolicitacao`) {
        if (!obsAprovPresidenciaVp) {
          // Errors.push(`Informe o motivo da reprovação 3`);
        }
      }
    }

    if (currentStateTxt == 'aprovarVerbaMaior') {
      // 1. Aprovar
      if (nextStateTxt == `enviarND`) {

      }
      // 2. Reprovar
      if (nextStateTxt == `validarEvidencias`) {
        if (!obsAprovVerbaMaior) {
          // Errors.push(`Informe o motivo da reprovação 4`);
        }
      }
    }

    if (currentStateTxt == 'aprovarVerbaMenor') {
      // 1. Aprovar
      if (nextStateTxt == `enviarND`) {

      }
      // 2. Reprovar
      if (nextStateTxt == `validarEvidencias`) {
        if (!obsAprovVerbaMenor) {
          // Errors.push(`Informe o motivo da reprovação 5`);
        }
      }
    }

    if (regras.enableNotificacaoCliente) {
      if (emailsCliente.length == 0) {
        Errors.push(`Informe os contatos no cliente que serão notificados`);
      }
      emailsCliente.forEach((email, index) => {
        if (!email.email_email) {
          Errors.push(`Informe o endereço de e-mail corretamente na linha ${index + 1}`);
        }
      })
    }

    if (regras.enableEvidencias) {
      if (arquivosEvidencias.filter(arquivo => !arquivo.arquivoEv_removed && !arquivo.arquivoEv_descricao).length > 0) {
        Errors.push(`Informe a descrição de todos os arquivos de evidências`);
      }
    }

    if (regras.enableValidacaoEvidencias) {
      // 1. Enviar para Aprovação
      if (nextStateTxt == `gtwAprovarVerbaMaior`) {

        if (form.getValue('valorLiberado') == '') {
          Errors.push(`Informe o valor liberado. Caso não tenha valor a liberar, informe 0,00`);
        }

        if (arquivosEvidencias.filter(arquivo => !arquivo.arquivoEv_removed && !arquivo.arquivoEv_aceito).length > 0) {
          Errors.push(`Para enviar para aprovação, dê o aceite em todos os arquivos de evidências`);
        }
      }
      // 2. Devolver para o Cliente
      if (nextStateTxt == `enviarEvidencias` && arquivosEvidencias.length > 0) {

        if (arquivosEvidencias.filter(arquivo => !arquivo.arquivoEv_removed && !arquivo.arquivoEv_aceito).length == 0) {
          Errors.push(`Recuse ao menos um arquivo para devolver ao cliente`);
        }

        if (arquivosEvidencias.filter(arquivo => !arquivo.arquivoEv_removed && !arquivo.arquivoEv_aceito).length > 0) {
          if (!motivoRecusaEv) {
            Errors.push(`Informe o motivo da recusa dos arquivos não aceitos`);
          }
        }
      }
      // 3. Devolver para Financeiro
      if (nextStateTxt == `conferirFinanceiro`) {
        if (arquivosEvidencias.filter(arquivo => !arquivo.arquivoEv_removed && !arquivo.arquivoEv_aceito).length > 0) {
          Errors.push(`Para devolver para o financeiro, dê o aceite em todos os arquivos de evidências`);
        }
      }
    }

    if (regras.enableND) {
      if (arquivosND.filter(arquivo => !arquivo.arquivoND_removed && !arquivo.arquivoND_descricao).length > 0) {
        Errors.push(`Informe a descrição de todos os arquivos de ND`);
      }

      if (arquivosND.filter(arquivo => !arquivo.arquivoND_removed && !arquivo.arquivoND_numero).length > 0) {
        Errors.push(`Informe o número da ND em todos os arquivos de ND`);
      }
    }

    if (regras.enableValidacaoND) {
      // 1. Aprovar
      if (nextStateTxt == `conferirFinanceiro`) {
        if (arquivosND.filter(arquivo => !arquivo.arquivoND_removed && !arquivo.arquivoND_aceito).length > 0) {
          Errors.push(`Para Aprovar, dê o aceite em todos os arquivos de ND`);
        }
      }
      // 2. Reprovar
      if (nextStateTxt == `enviarND` && arquivosND.length > 0) {
        if (arquivosND.filter(arquivo => !arquivo.arquivoND_removed && !arquivo.arquivoND_aceito).length == 0) {
          Errors.push(`Recuse ao menos um arquivo para devolver ao cliente`);
        }

        if (arquivosND.filter(arquivo => !arquivo.arquivoND_removed && !arquivo.arquivoND_aceito).length > 0) {
          if (!motivoRecusaND) {
            Errors.push(`Informe o motivo da recusa dos arquivos não aceitos`);
          }
        }
      }
    }

    if (regras.enableSelecionarDuplicatas) {
      // 1. Aprovar
      if (nextStateTxt == `aprovarPagamento`) {
        if (difValorLiberado != 0) {
          Errors.push(`O valor total das antecipações deve ser igual ao total liberado na ação`);
        }
      }
      // 2. Devolver para Marketing
      if (nextStateTxt == `validarEvidencias`) {
        if (!obsConferenciaFinanceiro) {
          Errors.push(`Para devolver para o Marketing, informe o motivo`);
        }
      }

      // 3. Finalizado sem Antecipação
      if (nextStateTxt == `finalizadoSemAntecipacao`) {

      }
    }

    if (currentStateTxt == 'aprovarPagamento') {
      // 1. Aprovar
      if (nextStateTxt == `gerarAbatimentos`) {

      }
      // 2. Reprovar
      if (nextStateTxt == `conferirFinanceiro`) {
        if (!obsAprovPagamento) {
          // Errors.push(`Informe o motivo da reprovação 6`);
        }
      }
    }

    if (regras.enableStatusErp) {

    }
  }

  if (Errors && Errors.length > 0) {
    throw Errors.join(`<br>`);
  }
}
