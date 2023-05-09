const campos = ['retorno', 'solicitacao', 'dtAtual'];
const display = campos;
const dePara = campos;


function defineStructure() {
  for (let i = 0; i < dePara.length; i++) {
    addColumn(dePara[i]);
  }
  addColumn('displaykey');

  setKey(['retorno']);

  addIndex(['retorno']);
}

function onSync(lastSyncDate) {
  return buscaDataset();
}

function createDataset(fields, constraints, sortFields) {
  return buscaDataset(fields, constraints, sortFields);
}

function onMobileSync(user) {

}

function buscaDataset(fields, constraints, sortFields) {

  log.info('******** totvs_atualiza_fluxo_marketing INICIO ');

  let params = getConstraints(constraints);

  let extSolicitacoes = getDataset('ext_mav', null, [
    // { field: 'solicitacao', value: '2296' },
    { field: 'pendenteTotvs', value: 'true' },
    // { field: 'tipoAcaoCodigo', value: 'spiff' },
    // { field: 'tipoSpiff', value: 'target' },
    // { field: 'solicitacao', value: '13461' },
  ]);

  // busca filhos e monta params 
  ttParams = {
    ttParam: [],
    ttRateioCategoria: [],
    ttSellout: [],
    ttSellinItem: [],
    ttSellinTarget: [],
    ttSellinTargetAc: [],
    ttSpiffItem: [],
    ttSpiffTarget: [],
    ttVpcEvt: [],
    ttVpcOutros: []
  }

  let solicitacaoCampos = [
    { name: 'solicitacao' }, { name: 'importado' }, { name: 'clienteCodigo' }, { name: 'tipoAcaoDescricao' }, { name: 'tipoAcaoCodigo' },
    { name: 'inicioAcao', type: 'date' }, { name: 'terminoAcao', type: 'date' }, { name: 'tipoQuantidade' }, { name: 'nomeAcao' },
    { name: 'tipoSellin' }, { name: 'tipoSellout' }, { name: 'tipoVpc' }, { name: 'tipoSpiff' }, { name: 'descricaoDetalhada' },
    { name: 'valorTotalVerba', type: 'decimal' }, { name: 'gpMedioSugerido', type: 'perc' }, { name: 'numControle' },
    { name: 'dataAbertura', type: 'date' }, { name: 'solicitanteNome' }, { name: 'solicitanteCodigo' }, { name: 'atividade' },
    { name: 'responsavel' }, { name: 'statusAprovGerMarketing' }, { name: 'dataAprovGerMarketing', type: 'date' },
    { name: 'userAprovGerMarketingNome' }, { name: 'userAprovGerMarketingCodigo' }, { name: 'obsAprovGerMarketing' },
    { name: 'statusAprovPresidenciaVp' }, { name: 'dataAprovPresidenciaVp', type: 'date' }, { name: 'userAprovPresidenciaVpNome' },
    { name: 'userAprovPresidenciaVpCodigo' }, { name: 'obsAprovPresidenciaVp' }, { name: 'status', ttName: 'statusSolicitacao' }, { name: 'motivoCancelamento' }
  ]

  // log.info(`solicitacoes.length = ${solicitacoes.length}`);

  extSolicitacoes.forEach(extSolicitacao => {

    let solicitacao = getDataset('marketing_abertura_verba', null, [
      { field: 'solicitacao', value: extSolicitacao.solicitacao },
      // { field: 'pendenteTotvs', value: 'S' },
      // { field: 'tipoAcaoCodigo', value: 'spiff' },
      // { field: 'tipoSpiff', value: 'target' },
      // { field: 'solicitacao', value: '13461' },
    ])[0];
    // if (Number(solicitacao.solicitacao) == 12478 || Number(solicitacao.solicitacao) == 2296 || Number(solicitacao.solicitacao) == 1872 || Number(solicitacao.solicitacao) == 1370) {

    let objSolicitacao = {};
    solicitacaoCampos.forEach(c => { objSolicitacao[c.ttName || c.name] = String(solicitacao[c.name]) == "null" ? "" : String(solicitacao[c.name]) == "NaN" ? "" : c.type == 'date' ? String(dateDDMMYYY(Number(solicitacao[c.name]), true), true) : replaceSpecialChars(String(solicitacao[c.name])) });

    ttParams.ttParam.push(objSolicitacao);

    [
      {
        tablename: 'rateioCategoria', tt: 'ttRateioCategoria', fieldPref: 'rateio',
        campos: [
          { name: 'categoriaCodigo' }, { name: 'categoriaDescricao' }, { name: 'perc', type: 'perc' }
        ]
      },
      {
        tablename: 'itensSellout', tt: 'ttSellout', fieldPref: 'itemSellout',
        campos: [
          { name: 'itemCodigo' }, { name: 'srpInicial', type: 'decimal' }, { name: 'netInicial', type: 'decimal' },
          { name: 'gpInicial', type: 'perc' }, { name: 'srpSugerido', type: 'decimal' }, { name: 'netSugerido', type: 'decimal' },
          { name: 'gpSugerido', type: 'perc' }, { name: 'rebateUnit', type: 'decimal' }, { name: 'qtde', type: 'decimal' },
          { name: 'rebateTotal', type: 'decimal' },
          { name: 'qtdEvidencia', type: 'decimal' }, { name: 'valEvidencia', type: 'decimal' }, { name: 'totEvidencia', type: 'decimal' },
        ]
      },
      {
        tablename: 'itensSellinIt', tt: 'ttSellinItem', fieldPref: 'itemSellinIt',
        campos: [
          { name: 'itemCodigo' }, { name: 'srpInicial', type: 'decimal' }, { name: 'netInicial', type: 'decimal' },
          { name: 'gpInicial', type: 'perc' }, { name: 'srpSugerido', type: 'decimal' }, { name: 'netSugerido', type: 'decimal' },
          { name: 'gpSugerido', type: 'perc' }, { name: 'rebateUnit', type: 'decimal' }, { name: 'qtde', type: 'decimal' },
          { name: 'rebateTotal', type: 'decimal' },
          { name: 'qtdEvidencia', type: 'decimal' }, { name: 'valEvidencia', type: 'decimal' }, { name: 'totEvidencia', type: 'decimal' },
        ]
      },
      {
        tablename: 'itensSellinTg', tt: 'ttSellinTarget', fieldPref: 'itemSellinTg',
        campos: [
          { name: 'descricao' }, { name: 'target' }, { name: 'qtde', type: 'decimal' }, { name: 'perc', type: 'perc' },
          { name: 'vlTarget', type: 'decimal' }, { name: 'vlTotal', type: 'decimal' },
          { name: 'qtdEvidencia', type: 'decimal' }, { name: 'valEvidencia', type: 'decimal' }, { name: 'totEvidencia', type: 'decimal' },
        ]
      },
      {
        tablename: 'itensSellinTgAc', tt: 'ttSellinTargetAc', fieldPref: 'itemSellinTgAc',
        campos: [
          { name: 'descricao' }, { name: 'target' }, { name: 'qtde', type: 'decimal' }, { name: 'perc', type: 'perc' },
          { name: 'vlTarget', type: 'decimal' }, { name: 'vlTotal', type: 'decimal' },
          { name: 'qtdEvidencia', type: 'decimal' }, { name: 'valEvidencia', type: 'decimal' }, { name: 'totEvidencia', type: 'decimal' },
        ]
      },
      {
        tablename: 'itensSpiffIt', tt: 'ttSpiffItem', fieldPref: 'itemSpiffIt',
        campos: [
          { name: 'itemCodigo' }, { name: 'spiffUnit', type: 'decimal' }, { name: 'qtde', type: 'decimal' },
          { name: 'vlTotal', type: 'decimal' },
          { name: 'qtdEvidencia', type: 'decimal' }, { name: 'valEvidencia', type: 'decimal' }, { name: 'totEvidencia', type: 'decimal' },
        ]
      },
      {
        tablename: 'itensSpiffTg', tt: 'ttSpiffTarget', fieldPref: 'itemSpiffTg',
        campos: [
          { name: 'foco' }, { name: 'target', type: 'perc' }, { name: 'qtde', type: 'decimal' },
          { name: 'vlUnit', type: 'decimal' }, { name: 'vlTotal', type: 'decimal' },
          { name: 'qtdEvidencia', type: 'decimal' }, { name: 'valEvidencia', type: 'decimal' }, { name: 'totEvidencia', type: 'decimal' },
        ]
      },
      {
        tablename: 'itensVpcEvt', tt: 'ttVpcEvt', fieldPref: 'itemVpcEvt',
        campos: [
          { name: 'nomeEvento' }, { name: 'finalidade' }, { name: 'inicio', type: 'date' }, { name: 'termino', type: 'date' },
          { name: 'perc', type: 'perc' }, { name: 'vlTotal', type: 'decimal' },
          { name: 'qtdEvidencia', type: 'decimal' }, { name: 'valEvidencia', type: 'decimal' }, { name: 'totEvidencia', type: 'decimal' },
        ]
      },
      {
        tablename: 'itensVpcOutros', tt: 'ttVpcOutros', fieldPref: 'itemVpcOutros',
        campos: [
          { name: 'tipo' }, { name: 'finalidade' }, { name: 'qtde', type: 'decimal' },
          { name: 'perc', type: 'perc' }, { name: 'vlTotal', type: 'decimal' },
          { name: 'qtdEvidencia', type: 'decimal' }, { name: 'valEvidencia', type: 'decimal' }, { name: 'totEvidencia', type: 'decimal' },
        ]
      },
      {
        tablename: 'arquivosND', tt: 'ttArquivosND', fieldPref: 'arquivoND',
        campos: [
          { name: 'numero' }, { name: 'aceito' }, { name: 'removed' },
        ]
      },

    ].forEach(paramTable => {

      getDataset('marketing_abertura_verba', null, [
        { field: 'tablename', value: paramTable.tablename },
        { field: 'documentid', value: solicitacao.documentid }
      ]).forEach(objTable => {
        let obj = { solicitacao: String(solicitacao.solicitacao) };

        paramTable.campos.forEach(c => {

          let value = String(objTable[`${paramTable.fieldPref}_${c.name}`] || '');
          obj[c.ttName || c.name] = String(value) == "null" ? "" : String(value) == "NaN" ? "" : c.type == 'date' ? String(dateDDMMYYY(Number(value), true)) : replaceSpecialChars(String(value));
        })

        if (!ttParams[paramTable.tt]) {
          ttParams[paramTable.tt] = [];
        }
        ttParams[paramTable.tt].push(obj);
      })
    })
    // }
  })

  let json = {};

  if (ttParams.ttParam.length > 0) {

    var properties = {};
    properties["receive.timeout"] = "60000";

    // log.info(`*** totvs_atualiza_fluxo_marketing 1 ${JSON.stringify(ttParams)}`);

    // const json = jsonLocal();
    try {
      json = callDatasul("esp/atualizaFluxoMarketing.p", "piCria", ttParams, null, properties);
    } catch (error) {
      extSolicitacoes.forEach(solicitacao => {
        if (solicitacao.statusIntegraTotvs != error) {
          // getDataset('fluig_atualiza_formulario', null, [
          //   { field: 'campos', value: 'pendenteTotvs|statusIntegraTotvs|dataIntegraTotvs' },
          //   { field: 'valores', value: `S|${String(error) || 'N/D'}|${String(new Date().getTime())}` },
          //   { field: 'documentid', value: String(solicitacao.documentid) }
          // ])
        }

      })
    }

    // log.info('*** totvs_atualiza_fluxo_marketing 2');

    if (json && json.ttStatus) {
      // log.info('*** totvs_atualiza_fluxo_marketing entrou na json.ttStatus')
      json.ttStatus.forEach(status => {

        // log.info('*** totvs_atualiza_fluxo_marketing solicitacao: ' + status.solicitacao);

        let solicitacao = extSolicitacoes.filter(s => s.solicitacao == status.solicitacao)[0];

        // log.info('*** totvs_atualiza_fluxo_marketing solicitacao.documentid: ' + solicitacao.documentid);
        // log.info('*** totvs_atualiza_fluxo_marketing status.retorno: ' + status.retorno);

        if (solicitacao) {

          getDataset('fluig_atualiza_formulario', null, [
            { field: 'campos', value: 'pendenteTotvs|statusIntegraTotvs|dataIntegraTotvs' },
            { field: 'valores', value: `false|${status.retorno || 'N/D'}|${String(new Date().getTime())}` },
            { field: 'documentid', value: String(solicitacao.documentid) }
          ])

        }
      })
    }

    // log.info('*** totvs_atualiza_fluxo_marketing 3');

  }

  // log.info('*** totvs_atualiza_fluxo_marketing 4');

  // log.info(JSON.stringify(json))

  return montaDataset(json.ttErro, json.ttStatus, campos, display, dePara, true);
}

function replaceSpecialChars(str) {
  return str
    .replace(/•/g, '*')
    .replace(/–/g, '-')
    .replace(/”/g, "'")
    .replace(/“/g, "'")
    .replace(/"/g, "'")
    .replace(/…/g, '.')
    // .replace(/[^-a-zA-Z0-9À-ÿ\t\r\n#°.,():;<>?!@$%&*{}\/ ]/g, "");
    .replace(/[•€„…†‡ˆ‰Š‹ŒŽš›œ—–žŸ¡Ÿ¢£¤¥¦¨©️ª«¬®️¯°±²³µ¶·¸¹º»¼½¾¿ÄÅÆËÎÏÐÖ×ØÜÝÞßäåæëïö÷øüýþÿ¢¥┐¤¦■±≡]/g, '')
    .replace(/[^-a-zA-Z0-9À-ÿ\t\r\n#°.,():;'?!@$%*{}[]\/ ]/g, '');
  // .substr(0,600)
}

/*$$ partials/getConstraintsParams.js $$*/
/*$$ partials/callDatasul.js $$*/
/*$$ partials/montaDataset.js $$*/
/*$$ partials/getDataset.js $$*/
/*$$ partials/dateDDMMYYYY.js $$*/

function jsonLocal() {
  return {
    ttStatus: [
      { retorno: 'OK' }
    ]
  };
}