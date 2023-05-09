function afterCancelProcess(colleagueId, processId) {
  hAPI.setCardValue("status", "CANCELADA");
  hAPI.setCardValue("motivoCancelamento", getValue("WKUserComment"));
  hAPI.setCardValue("pendenteTotvs", "S");
  enviaEmail("cancelamento", "S", "S", "S");
  atualizaPendenteTotvs(hAPI.getCardValue("solicitacao"));
}

function atualizaPendenteTotvs(solicitacao){

	var c1 = DatasetFactory.createConstraint("solicitacao",solicitacao, solicitacao, ConstraintType.MUST);
	var a = DatasetFactory.getDataset("ext_mav",["documentid","solicitacao","pendenteTotvs"], [c1], null);
	
	var c2 = DatasetFactory.createConstraint("campos", "pendenteTotvs", "pendenteTotvs", ConstraintType.MUST);
	var c3 = DatasetFactory.createConstraint("valores","true", "true", ConstraintType.MUST);
	var c4 = DatasetFactory.createConstraint("documentid",a.getValue(0, 'documentid'), a.getValue(0, 'documentid'), ConstraintType.MUST);
	
	DatasetFactory.getDataset("fluig_atualiza_formulario",null,
			[c2, c3, c4, c1], null);
}