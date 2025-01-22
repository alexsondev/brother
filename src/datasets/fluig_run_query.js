var atividades = {
  inicio: [1],
  validarMarketing: [2],
  revisarSolicitacao: [8],
  aprovarGerMarketing: [4],
  aprovarPresidencia: [6],
  analisarErros: [27, 36, 53, 54, 143, 125],
  aguardandoFimDaAcao: [129],
  autorizarNotificacaoInicio: [32],
  autorizarNotificacaoFim: [43],
  enviarEvidencias: [261],
  evidenciasControle: [280],
  validarEvidencias: [62],
  aprovarVerbaMaior: [151],
  aprovarVerbaMenor: [75],
  enviarND: [264],
  validarND: [103],
  conferirFinanceiro: [113],
  aprovarPagamento: [116],
  atualizarStatus: [132],
  gerarAbatimentos: [121],
  autorizarNotificacaoPagamento: [139],
  gerenciarVales: [202],
  gtwVales: [204],
  finalizado: [148, 206],
  finalizadoSemAntecipacao: [173]
};

function defineStructure() {

}

function onSync(lastSyncDate) {

}

function createDataset(fields, constraints, sortFields) {
  var dataset = DatasetBuilder.newDataset();


  // var campos = ['object_name', 'schema_name', 'type_desc', 'create_date', 'modify_date'];
  // var campos = ['object_name'];
  var campos = ['solicitacao', 'atividade', 'tipo'];

  campos.forEach(function (campo) {
    dataset.addColumn(campo);
  });
  // dataset.addColumn('EMAIL');
  // dataset.addColumn('ATIVIDADE');

  // var Documentos = executaSql(`SELECT name AS object_name FROM sys.columns WHERE object_id = OBJECT_ID('dbo.HISTOR_PROCES')  `, 'query', campos, '/jdbc/FluigDS');
  // var Documentos = executaSql(`SELECT name AS object_name, SCHEMA_NAME(schema_id) AS schema_name, type_desc, create_date, modify_date FROM sys.objects `, 'query', campos, '/jdbc/FluigDS');


  var data = executaSql(`
  SELECT 
    pw.NUM_PROCES,
    pw.START_DATE,
    pw.END_DATE,
    pw.STATUS,
    hp.NUM_SEQ_ESTADO,
    hp.NUM_SEQ_THREAD,
        MAX(hp.DAT_MOVTO) AS LAST_MOVTO
FROM 
    PROCES_WORKFLOW pw
JOIN 
    HISTOR_PROCES hp ON pw.NUM_PROCES = hp.NUM_PROCES
WHERE 
    pw.COD_DEF_PROCES = 'marketing_abertura_verba'
    AND pw.STATUS = 0
    AND hp.DAT_MOVTO BETWEEN '2024-12-19' AND '2025-01-08'
GROUP BY 

    pw.NUM_PROCES, pw.START_DATE, pw.END_DATE, pw.STATUS, hp.NUM_SEQ_ESTADO, hp.NUM_SEQ_THREAD`, 'query',
    ['NUM_PROCES', 'START_DATE', 'LAST_MOVTO', 'NUM_SEQ_ESTADO', 'NUM_SEQ_THREAD']
    , '/jdbc/FluigDS');


  // HISTOR_PROCES
  // log.info(Documentos)

  // var versaoAtiva = documentoPai[0].nr_versao;

  // var Documentos = executaSql("SELECT * FROM documento WHERE num_docto_propried = " + codDoctoPai + " and versao_ativa = 1 and tp_documento = 5 and cod_empresa = " + codEmpresa + " ", 'query', campos, '/jdbc/FluigDS');

  const solicitacoes = data.map(d => d.NUM_PROCES).filter((value, index, self) => self.indexOf(value) === index)
  log.info(JSON.stringify(solicitacoes))
  // // var Documentos = executaSql("SELECT * FROM documento WHERE nr_documento = 8549 and versao_ativa = 1 and tp_documento = 5 and cod_empresa = " + codEmpresa + " ", 'query', campos, '/jdbc/FluigDS');
  // // log.info(Documentos)
  solicitacoes.forEach(function (solicitacao) {
    log.info(solicitacao)
    const form = getDataset('marketing_abertura_verba', null, [
      { field: 'solicitacao', value: solicitacao }
    ])[0]
    //
    // log.info(documento.num_vers_propried);
    const tipo = getTipoEmail(form.status)
    // if (solicitacao.tipo) {


    var row = new Array();
    row.push(solicitacao);
    row.push(form.status);
    row.push(tipo);

    dataset.addRow(row);

    notifica(solicitacao, tipo)
    // }

    // if (documento.EMAIL != "ND") {

    //   notifica(documento.NUM_PROCES, documento.EMAIL);
    // }
    //     if (altera) {

    //       executaSql("UPDATE documento SET num_vers_propried = " + versaoAtiva + " WHERE cod_empresa = " + documento.cod_empresa + " and nr_documento = " + documento.nr_documento + " and nr_versao = " + documento.nr_versao, 'update', null, '/jdbc/FluigDS');

    //     }
    //   }
  });

  return dataset;

}

function onMobileSync(user) {

}

function getTipoEmail(etapa) {

  if (etapa == "ENVIO DE EVIDÊNCIAS") {
    return "evidencia"
  }
  if (etapa == "ENVIO DE ND") {
    return "envioND"
  }

  if (etapa == "ENVIO DE VALES") {
    return "vales"
  }

  // if (etapa == "Pagamento") {
  //   return "pagamento"
  // }
  return "nao notifica"
}

function findFieldNameByNumber(number) {
  var res = ""
  for (var key in atividades) {
    if (atividades[key].indexOf(number) >= 0) {
      res = key;
    }
  }
  return res; // Return null if the number is not found
}

function notifica(solicitacao, tipo) {
  if (tipo == 'nao notifica') {
    return
  }
  getDataset('fluig_notifica_acao_marketing', null, [
    { field: 'solicitacoes', value: solicitacao },
    { field: 'enviaBrother', value: 'S' },
    { field: 'enviaCliente', value: 'S' },
    { field: 'enviaExecutivo', value: 'S' },
    { field: 'tipo', value: tipo },
    // { field: 'email', value: 'flavio.h.serapiao@gmail.com' },
  ], true)
}

/*$$ partials/executaSql.js $$*/
/*$$ partials/getDataset.js $$*/