const campos = ['codigo', 'descricao'];
const display = ['codigo', 'descricao'];
const dePara = ['codigo', 'descricao'];


function defineStructure() {
  for (let i = 0; i < dePara.length; i++) {
    addColumn(dePara[i]);
  }
  addColumn('displaykey');

  setKey(['codigo']);

  addIndex(['codigo']);
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
  let params = getConstraints(constraints);

  var properties = {};
  properties["receive.timeout"] = "60000";

  // const json = jsonLocal();
  let json;

  try {
    json = callDatasul("esp/buscaCentroCusto.p", "piBusca", params, null, properties);
  } catch (error) {
    throw error;
  }

  return montaDataset(json.ttErro, json.ttCentroCusto, campos, display, null, true);
}

/*$$ partials/getConstraints.js $$*/
/*$$ partials/callDatasul.js $$*/
/*$$ partials/montaDataset.js $$*/

function jsonLocal() {
  return {
    ttCentroCusto: [
      { codigo: '100.100.100', descricao: 'CC TESTE' }
    ]
  };
}