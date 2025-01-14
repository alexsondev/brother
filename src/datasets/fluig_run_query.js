function defineStructure() {

}

function onSync(lastSyncDate) {

}

function createDataset(fields, constraints, sortFields) {
  var dataset = DatasetBuilder.newDataset();

  // var campos = ['object_name', 'schema_name', 'type_desc', 'create_date', 'modify_date'];
  var campos = ['NUM_PROCES', 'NM_DATASET', 'START_DATE', 'END_DATE', 'STATUS', 'LAST_MOVTO'];

  campos.forEach(function (campo) {
    dataset.addColumn(campo);
  });
  // dataset.addColumn('new_version');

  // var Documentos = executaSql(`SELECT name AS object_name, SCHEMA_NAME(schema_id) AS schema_name, type_desc, create_date, modify_date FROM sys.objects `, 'query', campos, '/jdbc/FluigDS');
  var Documentos = executaSql(`
SELECT 
    pw.NUM_PROCES, 
    d.NM_DATASET, 
    pw.START_DATE, 
    pw.END_DATE, 
    MAX(hp.DAT_MOVTO) AS LAST_MOVTO
FROM 
    PROCES_WORKFLOW pw
JOIN 
    DOCUMENTO d ON pw.NR_DOCUMENTO_CARD = d.NR_DOCUMENTO
JOIN 
    HISTOR_PROCES hp ON pw.NUM_PROCES = hp.NUM_PROCES
WHERE 
    pw.STATUS = 0
    AND hp.DAT_MOVTO BETWEEN '2024-12-27' AND '2025-01-08'
GROUP BY 
    pw.NUM_PROCES, 
    d.NM_DATASET, 
    pw.START_DATE, 
    pw.END_DATE `, 'query', campos, '/jdbc/FluigDS');


  // HISTOR_PROCES
  // log.info(Documentos)

  // var versaoAtiva = documentoPai[0].nr_versao;

  // var Documentos = executaSql("SELECT * FROM documento WHERE num_docto_propried = " + codDoctoPai + " and versao_ativa = 1 and tp_documento = 5 and cod_empresa = " + codEmpresa + " ", 'query', campos, '/jdbc/FluigDS');
  // // var Documentos = executaSql("SELECT * FROM documento WHERE nr_documento = 8549 and versao_ativa = 1 and tp_documento = 5 and cod_empresa = " + codEmpresa + " ", 'query', campos, '/jdbc/FluigDS');
  // // log.info(Documentos)
  Documentos.forEach(function (documento) {
    //   // log.info(documento.num_vers_propried);
    log.info(documento);
    //   if (documento.num_vers_propried != versaoAtiva) {
    var row = new Array();
    campos.forEach(function (campo) {
      row.push(documento[campo]);
    });
    //     row.push(String(versaoAtiva));

    dataset.addRow(row);

    //     if (altera) {

    //       executaSql("UPDATE documento SET num_vers_propried = " + versaoAtiva + " WHERE cod_empresa = " + documento.cod_empresa + " and nr_documento = " + documento.nr_documento + " and nr_versao = " + documento.nr_versao, 'update', null, '/jdbc/FluigDS');

    //     }
    //   }
  });

  return dataset;

}

function onMobileSync(user) {

}

/*$$ partials/executaSql.js $$*/
