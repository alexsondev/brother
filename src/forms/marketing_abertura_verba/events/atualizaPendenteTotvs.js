function atualizaPendenteTotvs(form) {
  const solicitacao = form.getValue('solicitacao');

  log.info('atualizaPendenteTotvs solicitacao');
  if (solicitacao) {
    const extMav = getDataset('ext_mav', ['documentid', 'solicitacao', 'pendenteTotvs'], [
      { field: 'solicitacao', value: form.getValue('solicitacao') }
    ])[0];

    if (extMav) {
      getDataset('fluig_atualiza_formulario', null, [
        { field: 'campos', value: 'pendenteTotvs' },
        { field: 'valores', value: 'true' },
        { field: 'documentid', value: String(extMav.documentid) }
      ]);
    }
  }
  log.info('atualizaPendenteTotvs fim');
}
