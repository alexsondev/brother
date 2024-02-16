/*eslint-disable*/
/*jshint -W116 */
function inputFields(form) {

  const Params = getParams(form);

  const currentState = getValue("WKNumState");
  const nextState = getValue("WKNextState");

  const clienteCodigo = value(form, `clienteCodigo`);
  const clienteNome = value(form, `clienteNome`);
  const nomeAcao = value(form, `nomeAcao`);
  const solicitacao = value(form, `solicitacao`);
  const tipoAcaoCodigo = value(form, `tipoAcaoCodigo`);
  const suspenderAcao = value(form, `suspenderAcao`);
  const inicioAcao = value(form, `inicioAcao`);
  const terminoAcao = value(form, `terminoAcao`);
  const solicitanteCodigo = value(form, `solicitanteCodigo`);

  const arquivosEvidencias = getChildren(form, `arquivosEvidencias`,
    [`arquivoEv_nome`, `arquivoEv_type`, `arquivoEv_documentid`, `arquivoEv_version`,
      `arquivoEv_url`, `arquivoEv_removed`, `arquivoEv_descricao`, `arquivoEv_aceito`,
      `arquivoEv_motivoRecusa`]);

  // itemSellinIt itensSellinIt
  // itemprice itensprice
  // itemSpiffIt itensSpiffIt

  const arquivosND = getChildren(form, `arquivosND`,
    [`arquivoND_nome`, `arquivoND_type`, `arquivoND_documentid`, `arquivoND_version`,
      `arquivoND_url`, `arquivoND_removed`, `arquivoND_descricao`, `arquivoND_aceito`,
      `arquivoND_motivoRecusa`, `arquivoND_numero`]);

  const displaykey = `${suspenderAcao ? 'SUSPENSA - ' : ''} ${clienteNome}`;

  log.info(`getValue('WKUser') = ${getValue('WKUser')}`);
  log.info(`currentState = ${currentState}`);

  const dsUser = getDataset('colleague', ['colleagueId', 'colleagueName', 'mail', 'login'], [
    { field: 'login', value: String(getValue('WKUser')) }
  ], true)[0];

  form.setValue('displaykey', displaykey);

  atualizaPendenteTotvs(form);

  
  if (currentState == Params.atividades.inicio[0]) {

    // preenche data de abertura
    form.setValue('dataAbertura', new Date().getTime());

    // preenche solicitante
    const solicitante = getDataset('colleague', ['colleagueId', 'colleagueName', 'mail', 'login'], [
      { field: 'login', value: solicitanteCodigo }
    ], true)[0];
    if (solicitante) {

      form.setValue('solicitante', JSON.stringify(solicitante));
    }

    // preenche cliente
    const cliente = getDataset('totvs_busca_cliente', null, [
      { field: "codigo", value: clienteCodigo },
    ], true)[0];

    if (cliente) {
      log.info(`JSON.stringify(cliente): ${JSON.stringify(cliente)}`)

      form.setValue('clienteCodigo', cliente.codigo);
      form.setValue('clienteNome', cliente.razaoSocial);
      form.setValue('cliente', JSON.stringify(cliente));
    }
    // preenche tipo de ação
    const tipoAcao = getDataset('marketing_tipo_acao', ["tipoAcao", "tipoAcaoCodigo", "descricaoTipoAcao", "displaykey", "contaContabil"], [
      { field: "tipoAcaoCodigo", value: tipoAcaoCodigo },
    ])[0];
    if (tipoAcao) {
      tipoAcao.contaContabil = JSON.parse(tipoAcao.contaContabil)
      log.info(`JSON.stringify(tipoAcao): ${JSON.stringify(tipoAcao)}`)

      form.setValue('tipoAcaoDescricao', tipoAcao.displaykey);
      form.setValue('tipoAcao', JSON.stringify(tipoAcao));
    }


    // se formato da data for 99/99/9999, converter para timestamp
    if (inicioAcao?.indexOf('/') > -1) {
      form.setValue('inicioAcao', new Date(inicioAcao).getTime());
    }
    if (terminoAcao?.indexOf('/') > -1) {
      form.setValue('terminoAcao', new Date(terminoAcao).getTime());
    }

    // preenche tabelas de itens
    [
      { table: "itensSellout", child: "itemSellout" },
      { table: "itensSellinIt", child: "itemSellinIt" },
      { table: "itensprice", child: "itemprice" },
      { table: "itensSpiffIt", child: "itemSpiffIt" },
    ].forEach(({ table, child }) => {

      log.info(`${child}_itemCodigo:${child}_itemCodigo`)

      const itens = getChildren(form, table, [`${child}_itemCodigo`]);
      itens.forEach((item, i) => {

        log.info(`item[${child}_itemCodigo]:, item[${child}_itemCodigo]`)

        const itemDataset = getDataset('totvs_busca_item', null, [
          { field: "codigo", value: item[`${child}_itemCodigo`] },
        ], true)[0];
        if (itemDataset) {

          log.info(`JSON.stringify(itemDataset): ${JSON.stringify(itemDataset)}`);

          form.setValue(`${child}_item___${i + 1}`, JSON.stringify(itemDataset));
        }
      })
    })

    
    const rateio = getChildren(form, "rateioCategoria", [`rateio_categoriaCodigo`]);
    const categorias = getDataset('totvs_busca_business_segment')
    rateio.forEach((item, i) => {
      const categoria = categorias.filter(cat => cat.codigo == item.rateio_categoriaCodigo)[0]
      if (categoria) {

        log.info(`JSON.stringify(categoria): ${JSON.stringify(categoria)}`);

        form.setValue(`rateio_categoria___${i + 1}`, JSON.stringify(categoria));
      }
    })
    

    // itensSellout.forEach((itemSellout,i) => {
    //   const item = getDataset('totvs_busca_item', null, [
    //     { field: "codigo", value: itemSellout.itemSellout_itemCodigo },
    //   ], true);

    //   form.setValue( `itemSellout_item___${i + 1}`, JSON.stringify(item[0]))
    //   form.setValue( `itemSellout_itemDescricao___${i + 1}`, JSON.stringify(item[0].descricao))
    // })
  }

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