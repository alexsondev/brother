const campos = ['solicitacao', 'pendenteTotvs'];
const display = campos;
const dePara = campos;


function defineStructure() {
  for (let i = 0; i < dePara.length; i++) {
    addColumn(dePara[i]);
  }
  addColumn('displaykey');

  setKey(['solicitacao']);

  addIndex(['solicitacao']);
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

  if (!params.solicitacao) {
    throw 'Informe o código da solicitação';
  }

  let extSolicitacao = getDataset('ext_mav', null, [
    { field: 'solicitacao', value: params.solicitacao },
    // { field: 'pendenteTotvs', value: 'true' },
    // { field: 'tipoAcaoCodigo', value: 'spiff' },
    // { field: 'tipoSpiff', value: 'target' },
    // { field: 'solicitacao', value: '13461' },
  ]);

  if (!extSolicitacao[0]) {
    createCardIndex(334176, params)
  }

  return montaDataset(ttErro, ttStatus, campos, display, dePara, true);
}

/*$$ partials/getConstraintsParams.js $$*/
/*$$ partials/createCardIndex.js $$*/
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