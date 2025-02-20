function afterProcessCreate(processId) {
  hAPI.setCardValue("solicitacao", processId);
  // hAPI.setCardValue("numControle", processId);
  

  DatasetFactory.getDataset("fluig_cria_ext_mav", null, [
    DatasetFactory.createConstraint("solicitacao", processId, processId, ConstraintType.MUST)
  ], null);

  hAPI.setCardValue("guid", guid());
}

function guid() {
  function s4() {
    return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
  }
  return s4() + s4() + '$' + s4() + '$' + s4() + '$' + s4() + '$' + s4() + s4() + s4();
}