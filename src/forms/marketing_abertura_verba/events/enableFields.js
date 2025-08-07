function enableFields(form) {
  const Params = getParams(form);
  const currentState = getValue(`WKNumState`);
  const nextState = getValue(`WKNextState`);
  const formMode = form.getFormMode();


  let currentStateTxt = 'inicio';

  for (var atividade in Params.atividades) {
    if (Params.atividades[atividade].indexOf(parseInt(currentState)) > -1) {
      currentStateTxt = atividade;
    }
  }

  log.info(`currentStateTxt = ${currentStateTxt}`);

  if (currentStateTxt !== 'inicio' && currentStateTxt !== 'revisarSolicitacao') {
    form.setEnabled("inicioAcao_i", false);
    form.setEnabled("inicioAcao_f", false);
    // form.setEnabled("inicioAcao", false);
    form.setEnabled("terminoAcao_i", false);
    form.setEnabled("terminoAcao_f", false);
    // form.setEnabled("terminoAcao", false);
  }
  
}