const campos = ['name', 'value'];
const display = campos;
const dePara = campos;

function createDataset(fields, constraints, sortFields) {
  return buscaDataset(fields, constraints, sortFields);
}

function onMobileSync(user) {

}

function buscaDataset(fields, constraints, sortFields) {
  let params = getConstraints(constraints);
  let dsForm = [];
  let tables = String(params.tables).split(';');

  let dsSolicitacao = getDataset(params.dataset, null, [
    { field: 'documentid', value: params.documentid }
  ]);

  dsSolicitacao = dsSolicitacao[dsSolicitacao.length - 1];

  if (dsSolicitacao) {

    let dsWorkflowProcess = getDataset('workflowProcess', null, [
      { field: 'workflowProcessPK.processInstanceId', value: dsSolicitacao.solicitacao }
    ])[0];

    let dsProcessTask = getDataset('processTask', null, [
      { field: 'processTaskPK.processInstanceId', value: dsSolicitacao.solicitacao },
      { field: 'active', value: true },
    ]);

    let portalProcessTask = dsProcessTask.filter(p => p['processTaskPK.colleagueId'] == getValue('WKUser'))
    if (portalProcessTask.length == 0) portalProcessTask = dsProcessTask;

    if (dsWorkflowProcess) {
      dsForm.push({
        name: 'wfVersion',
        value: String(dsWorkflowProcess.version)
      })
    }
    if (portalProcessTask[0]) {
      dsForm.push({
        name: 'currentMovto',
        value: String(portalProcessTask[0]['processTaskPK.movementSequence'])
      })
    }

    for (var col in dsSolicitacao) {
      dsForm.push({
        name: col,
        value: String(dsSolicitacao[col])
      })
    }

    tables.forEach(tablename => {
      let dsChildren = getDataset(params.dataset, null, [
        { field: 'documentid', value: params.documentid },
        { field: 'tablename', value: tablename },
        { field: 'version', value: dsSolicitacao.version },
      ]);
      dsChildren.forEach((child, index) => {
        for (var col in child) {
          dsForm.push({
            name: `${col}___${index + 1}`,
            value: String(child[col])
          })
        }
      })
    })

  }




  return montaDataset(null, dsForm, campos, display);
}

/*$$ partials/getConstraintsParams.js $$*/
/*$$ partials/callDatasul.js $$*/
/*$$ partials/montaDataset.js $$*/
/*$$ partials/getDataset.js $$*/

function jsonLocal() {
  return {
    ttValores: [
      { netInicial: 100, netSugerido: 120, gpInicial: 130, gpSugerido: 140, dolar: 5 }
    ]
  };
}