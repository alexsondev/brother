const campos = ['retorno', 'solicitacao', 'sequencia', 'dtAtual'];
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

  log.info('******** totvs_atualiza_fluxo_marketing TESTE PROD 28-01 ');

  let params = getConstraints(constraints);

  let extSolicitacoes = getDataset('ext_mav', null, [
    // { field: 'solicitacao', value: '2296' },
    { field: 'pendenteTotvs', value: 'true' },
    // { field: 'tipoAcaoCodigo', value: 'spiff' },
    // { field: 'tipoSpiff', value: 'target' },
    // { field: 'solicitacao', value: '13461' },
  ]);

  // busca filhos e monta params 

  let solicitacaoCampos = [
    { name: 'solicitacao' }, { name: 'importado' }, { name: 'clienteCodigo' }, { name: 'tipoAcaoDescricao' }, { name: 'tipoAcaoCodigo' },
    { name: 'inicioAcao', type: 'date' }, { name: 'terminoAcao', type: 'date' }, { name: 'tipoQuantidade' }, { name: 'nomeAcao' },
    { name: 'tipoPrpro' }, { name: 'tipoSellin' }, { name: 'tipoSellout' }, { name: 'tipoVpc' }, { name: 'tipoSpiff' }, { name: 'descricaoDetalhada' },
    { name: 'valorTotalVerba', type: 'decimal' }, { name: 'gpMedioSugerido', type: 'perc' }, { name: 'numControle' },
    { name: 'dataAbertura', type: 'date' }, { name: 'solicitanteNome' }, { name: 'solicitanteCodigo' }, { name: 'atividade' },
    { name: 'responsavel' }, { name: 'statusAprovGerMarketing' }, { name: 'dataAprovGerMarketing', type: 'date' },
    { name: 'userAprovGerMarketingNome' }, { name: 'userAprovGerMarketingCodigo' }, { name: 'obsAprovGerMarketing' },
    { name: 'statusAprovPresidenciaVp' }, { name: 'dataAprovPresidenciaVp', type: 'date' }, { name: 'userAprovPresidenciaVpNome' },
    { name: 'userAprovPresidenciaVpCodigo' }, { name: 'obsAprovPresidenciaVp' }, { name: 'status', ttName: 'statusSolicitacao' }, { name: 'motivoCancelamento' },
    { name: "guid" }, { name: "valorLiberado" }

  ]

  log.info(`solicitacoes.length = ${extSolicitacoes.length}`);

  let json = {};
  let ttErro = [];
  let ttStatus = [];

  extSolicitacoes
    .filter((extSolicitacao) => Number(extSolicitacao.documentid) >= 183320)
    .forEach((extSolicitacao, seq) => {
      // extSolicitacoes.forEach(async (extSolicitacao) => {

      log.info(`extSolicitacao.documentid = ${extSolicitacao.documentid}`);

      let ttParams = {
        ttParam: [],
        ttRateioCategoria: [],
        ttSellout: [],
        ttPrpro: [],
        ttSellinItem: [],
        ttSellinTarget: [],
        ttSellinTargetAc: [],
        ttSpiffItem: [],
        ttSpiffTarget: [],
        ttVpcEvt: [],
        ttVpcOutros: []
      }

      let solicitacao = getDataset('marketing_abertura_verba', null, [
        { field: 'solicitacao', value: extSolicitacao.solicitacao },
        // { field: 'pendenteTotvs', value: 'S' },
        // { field: 'tipoAcaoCodigo', value: 'spiff' },
        // { field: 'tipoSpiff', value: 'target' },
        // { field: 'solicitacao', value: '13461' },
      ])[0];
      // if (Number(solicitacao.solicitacao) == 12478 || Number(solicitacao.solicitacao) == 2296 || Number(solicitacao.solicitacao) == 1872 || Number(solicitacao.solicitacao) == 1370) {

      let objSolicitacao = {};
      solicitacaoCampos.forEach(c => {
        try {

          objSolicitacao[c.ttName || c.name] = String(solicitacao[c.name]) == "null" ? "" : String(solicitacao[c.name]) == "NaN" ? "" : c.type == 'date' ? String(dateDDMMYYY(Number(solicitacao[c.name]), true), true) : replaceSpecialChars(String(solicitacao[c.name]))
        } catch (error) {
          objSolicitacao[c.ttName || c.name] = ""
        }
      });

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
          tablename: "itensPrpro",
          tt: "ttPrpro",
          fieldPref: "itemPrpro",
          campos: [{
            name: "itemCodigo"
          }, {
            name: "srpInicial",
            type: "decimal"
          }, {
            name: "netInicial",
            type: "decimal"
          }, {
            name: "gpInicial",
            type: "perc"
          }, {
            name: "srpSugerido",
            type: "decimal"
          }, {
            name: "netSugerido",
            type: "decimal"
          }, {
            name: "gpSugerido",
            type: "perc"
          }, {
            name: "rebateUnit",
            type: "decimal"
          }, {
            name: "qtde",
            type: "decimal"
          }, {
            name: "rebateTotal",
            type: "decimal"
          }, {
            name: "qtdEvidencia",
            type: "decimal"
          }, {
            name: "valEvidencia",
            type: "decimal"
          }, {
            name: "totEvidencia",
            type: "decimal"
          }]
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
        try {

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
        } catch (error) {
          log.info(`~ //paramTable.forEach ~ error: ${error}`)

        }
      })


      var properties = {};
      properties["receive.timeout"] = "60000";

      log.info(`*** totvs_atualiza_fluxo_marketing 1 ${JSON.stringify(ttParams)}`);

      // const json = jsonLocal();

      try {
        json = callDatasul("esp/atualizaFluxoMarketing.p", "piCria", ttParams, null, properties);
        ttStatus.concat(json.ttStatus)
      } catch (error) {
        log.info(`~ //extSolicitacoes.forEach ~ error: ${error}`)

        json = {}
        ttErro.push({
          mensagem: `THROW ${solicitacao.solicitacao} ${seq}: ${error}`
        })
      }

      log.info(`*** totvs_atualiza_fluxo_marketing 2. json: ${JSON.stringify(json)}`);

      if (json && json.ttStatus) {
        // log.info('*** totvs_atualiza_fluxo_marketing entrou na json.ttStatus')
        getDataset('fluig_atualiza_formulario', null, [
          { field: 'campos', value: 'pendenteTotvs|statusIntegraTotvs|dataIntegraTotvs' },
          { field: 'valores', value: `false|${json.ttStatus[0].retorno || 'N/D'}|${String(new Date().getTime())}` },
          { field: 'documentid', value: String(extSolicitacao.documentid) }
        ], true)
      }

      // }
    });


  // log.info(`~ saiu extSolicitacoes.forEach ~ ttStatus: ${JSON.stringify(ttStatus)}`)

  return montaDataset(ttErro, ttStatus, campos, display, dePara, true);
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